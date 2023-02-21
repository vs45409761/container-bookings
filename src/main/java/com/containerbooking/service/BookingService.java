package com.containerbooking.service;

import com.containerbooking.client.ContainerAvailabilityClient;
import com.containerbooking.entity.Bookings;
import com.containerbooking.model.BookingContainer;
import com.containerbooking.model.BookingRef;
import com.containerbooking.model.ContainerBooking;
import com.containerbooking.repository.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ContainerAvailabilityClient client;
    private final BookingRepo bookingRepository;


    public Mono<Map<String, Boolean>>  checkAvailability(BookingContainer bookings){
        return client.checkAvailability(bookings)
                .map(availability -> {
                    Boolean isAvailable = availability.getAvailableSpace() > 0;
                    return Collections.singletonMap("available", isAvailable);
                })
                .defaultIfEmpty((Collections.singletonMap("available", Boolean.TRUE)));


    }
    public Mono<BookingRef> saveBooking(ContainerBooking bookingRequest) {
            Bookings booking = processBookingRequest(bookingRequest);
        return bookingRepository.persistBookings(booking);

    }
    private Bookings processBookingRequest(ContainerBooking request) {
             return Bookings.builder().containerSize(request.getContainerSize())
                     .origin(request.getOrigin())
                     .destination(request.getDestination())
                     .quantity(request.getQuantity())
                     .timeStamp(request.getTimestamp().atZone(ZoneOffset.UTC).toInstant()).build();

    }


}
