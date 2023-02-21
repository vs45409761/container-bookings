package com.containerbooking.repository;

import com.containerbooking.entity.Bookings;
import com.containerbooking.model.BookingRef;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class BookingRepo {

   private final BookingRepository bookingRepository;
    public Mono<BookingRef> persistBookings(Bookings bookings){
        return bookingRepository.save(bookings)
                .map(savedBooking -> new BookingRef(savedBooking.getId()))
                .onErrorResume(throwable -> {
                    System.out.println("Error saving booking: " + throwable.getMessage());
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Sorry there was a problem processing your request"));
                });

    }

}
