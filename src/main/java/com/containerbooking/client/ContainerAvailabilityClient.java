package com.containerbooking.client;

import com.containerbooking.model.AvailableSpace;
import com.containerbooking.model.BookingContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ContainerAvailabilityClient {
    WebClient webClient = WebClient.create("http://localhost:8082/api/bookings");
    public Mono<AvailableSpace> checkAvailability(BookingContainer bookings) {

        return webClient.post()
                .uri("/checkAvailable")
                .body(BodyInserters.fromValue(bookings))
                .retrieve()
                .bodyToMono(AvailableSpace.class)
                .onErrorReturn(new AvailableSpace(6));
    }
}
