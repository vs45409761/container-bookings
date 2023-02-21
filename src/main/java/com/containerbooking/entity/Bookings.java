package com.containerbooking.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;


@Table("Bookings")
@Data
@Builder
public class Bookings {
    @PrimaryKeyColumn(name = "booking_ref", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @Column("container_type")
    private String containerType;

    @Column("container_size")
    private Integer containerSize;
    private String origin;

    private String destination;
    private Integer quantity;

    private Instant timeStamp;

}
