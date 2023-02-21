package com.containerbooking.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContainerBooking extends BookingContainer {

    private LocalDateTime timestamp;
}
