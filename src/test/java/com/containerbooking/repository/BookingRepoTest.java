package com.containerbooking.repository;

import com.containerbooking.entity.Bookings;
import com.containerbooking.model.BookingRef;
import com.containerbooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class BookingRepoTest {

    @MockBean
    private BookingRepository bookingRepository;

    BookingRepo bookingRepo;


    @BeforeEach
    public void setUp() throws Exception {
         bookingRepo = new BookingRepo(bookingRepository);
    }

    @Test
    public void shouldSaveBookingDetails(){
        Bookings bookings = Bookings.builder().id("957000001").build();

        given(bookingRepository.save(bookings)).willReturn(Mono.just(bookings));

       Mono<BookingRef> bookRef =  bookingRepo.persistBookings(bookings);
       BookingRef bf = bookRef.block();

        assert bf != null;
        assertEquals(bf.getBookingRef(), bookings.getId());



    }

}