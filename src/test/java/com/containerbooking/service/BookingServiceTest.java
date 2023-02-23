package com.containerbooking.service;

import com.containerbooking.client.ContainerAvailabilityClient;
import com.containerbooking.entity.Bookings;
import com.containerbooking.model.AvailableSpace;
import com.containerbooking.model.BookingContainer;
import com.containerbooking.model.BookingRef;
import com.containerbooking.model.ContainerBooking;
import com.containerbooking.repository.BookingRepo;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import javax.annotation.security.RunAs;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
class BookingServiceTest {


    BookingService bookingService;

    @MockBean
    ContainerAvailabilityClient client;

    @MockBean
    BookingRepo bookingRepo;

    @Captor
    ArgumentCaptor<Bookings> bookingsCaptor;

    @BeforeEach
    public void setUp() throws Exception {
        bookingService = new BookingService(client, bookingRepo);
    }



    @Test
    public void shouldReturnContainerAsAvailableWhenAvailableSpaceIsNotZero(){
        BookingContainer bookingContainer = BookingContainer.builder()
                .containerSize(20)
                .containerType(BookingContainer.ContainerType.DRY)
                .destination("Singapore")
                .origin("Southampton").quantity(5).build();

        given(client.checkAvailability(bookingContainer))
                .willReturn(Mono.just(AvailableSpace.builder().availableSpace(6).build()));

        Mono<BookingRef> availability = bookingService.checkAvailability(bookingContainer);

        BookingRef bookingRef = availability.block();

        assert bookingRef != null;
        assertTrue(bookingRef.isAvailable());

    }
    @Test
    public void shouldReturnContainerIsNotAvailableWhenAvailableSpaceIsZero(){
        BookingContainer bookingContainer = BookingContainer.builder()
                .containerSize(20)
                .containerType(BookingContainer.ContainerType.DRY)
                .destination("Singapore")
                .origin("Southampton").quantity(5).build();

        given(client.checkAvailability(bookingContainer))
                .willReturn(Mono.just(AvailableSpace.builder().availableSpace(0).build()));

        Mono<BookingRef> availability = bookingService.checkAvailability(bookingContainer);

        BookingRef bookingRef = availability.block();

        assert bookingRef != null;
        assertFalse(bookingRef.isAvailable());
    }
    @Test
    public void shouldReturnBookingReferenceNumber(){
        ContainerBooking container = ContainerBooking.builder()
                .containerSize(20)
                .containerType(BookingContainer.ContainerType.DRY)
                .destination("Singapore")
                .origin("Southampton").quantity(5)
                .timestamp(LocalDateTime.now()).build();

        given(bookingRepo.persistBookings(bookingsCaptor.capture()))
                .willReturn(Mono.just(BookingRef.builder().bookingRef("957000001").build()));

        Mono<BookingRef> bookingRefMono = bookingService.saveBooking(container);

        BookingRef bookingRef = bookingRefMono.block();

        assert bookingRef != null;
        assertEquals(bookingRef.getBookingRef(), "957000001" );
    }

}