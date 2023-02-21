package com.containerbooking.controller;

import com.containerbooking.model.BookingContainer;
import com.containerbooking.model.BookingRef;
import com.containerbooking.model.ContainerBooking;
import com.containerbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingsController {
   private final BookingService bookingService;

    @PostMapping
    public Mono<Map<String, Boolean>> checkAvailability(@Valid @RequestBody BookingContainer bookingRequest) {
        return bookingService.checkAvailability(bookingRequest);
    }
    @PostMapping
    public Mono<BookingRef> containerBooking(@Valid @RequestBody ContainerBooking bookingRequest){
        return bookingService.saveBooking(bookingRequest);
    }










}
