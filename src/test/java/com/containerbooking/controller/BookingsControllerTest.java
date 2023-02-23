package com.containerbooking.controller;

import com.containerbooking.model.BookingContainer;
import com.containerbooking.model.BookingRef;
import com.containerbooking.model.ContainerBooking;
import com.containerbooking.service.BookingService;
import com.google.common.base.Verify;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import javax.annotation.security.RunAs;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BookingsController.class)
class BookingsControllerTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    BookingService bookingService;
    @Test
    public void shouldReturnAsContainerAvailable(){

        //Given

        BookingContainer bookingContainer = BookingContainer.builder()
                .containerSize(20)
                .containerType(BookingContainer.ContainerType.DRY)
                .destination("Singapore")
                .origin("Southampton").quantity(5).build();

        given(bookingService.checkAvailability(bookingContainer))
                .willReturn(Mono.just(BookingRef.builder().available(true).build()));

        //when
        WebTestClient.ResponseSpec responseSpec = invokeCheckAvailabilityService(bookingContainer);

        //Then
        responseSpec.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.available").isBoolean();

    }
    @Test
    public void shouldThrowError(){

        BookingContainer bookingContainer = BookingContainer.builder()
                .containerSize(20)
                .containerType(BookingContainer.ContainerType.DRY)
                .destination("we")
                .origin("Southampton").quantity(5).build();

       WebTestClient.ResponseSpec responseSpec = invokeCheckAvailabilityService(bookingContainer);

        responseSpec.expectStatus().isBadRequest();
        Mockito.verifyNoMoreInteractions(bookingService);
    }

    private WebTestClient.ResponseSpec invokeCheckAvailabilityService(BookingContainer bookingContainer){
       return webTestClient.post().uri("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(bookingContainer), BookingContainer.class)
                .exchange();
    }

    @Test
    public void shouldReturnBookingReferenceNumber(){
        ContainerBooking containerBooking = ContainerBooking.builder()
                .containerSize(20)
                .containerType(BookingContainer.ContainerType.DRY)
                .destination("Singapore")
                .origin("Southampton").quantity(5)
                .timestamp(LocalDateTime.now())
                .build();

        given(bookingService.saveBooking(containerBooking))
                .willReturn(Mono.just(BookingRef.builder().bookingRef("957000001").build()));

        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/api/bookings/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(containerBooking), ContainerBooking.class)
                .exchange();

        responseSpec.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.bookingRef").isEqualTo("957000001");
    }







}