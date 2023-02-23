package com.containerbooking.client;

import com.containerbooking.model.AvailableSpace;
import com.containerbooking.model.BookingContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

class ContainerAvailabilityClientTest {

    private static MockWebServer mockServer;
    private static WebClient webClient;

    private ContainerAvailabilityClient client = new ContainerAvailabilityClient();

    private static final ObjectMapper objectMapper = new ObjectMapper();;

    @BeforeAll
    @SneakyThrows
    static void setUp(){
        mockServer = new MockWebServer();
        mockServer.start(8082);
        webClient = WebClient.create(mockServer.url("/api/bookings").toString());
    }

    @AfterAll
    @SneakyThrows
    static void tearDown(){
        mockServer.shutdown();
    }
    @Test
    @SneakyThrows
    public void shouldAbleToReturnContainerAvailability(){

        AvailableSpace availableSpace = new AvailableSpace(3);
        BookingContainer bookingContainer = new BookingContainer();

        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(availableSpace))
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        Mono<AvailableSpace> availableSpaceMono = client.checkAvailability(bookingContainer);

        AvailableSpace availableSpaceResp = availableSpaceMono.block();
        assert availableSpaceResp != null;
        Assertions.assertEquals(availableSpace.getAvailableSpace(), availableSpaceResp.getAvailableSpace());
    }


    @SneakyThrows
    @Test
    public void shouldReturnContainerAvailabilityFixedValueOnApiFailure(){
        mockServer.shutdown();
        AvailableSpace availableSpace = new AvailableSpace(3);
        BookingContainer bookingContainer = new BookingContainer();
        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(availableSpace))
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        Mono<AvailableSpace> availableSpaceMono = client.checkAvailability(bookingContainer);

        AvailableSpace availableSpaceResp = availableSpaceMono.block();

        assert availableSpaceResp != null;
        Assertions.assertNotEquals(availableSpace.getAvailableSpace(), availableSpaceResp.getAvailableSpace());

    }




}