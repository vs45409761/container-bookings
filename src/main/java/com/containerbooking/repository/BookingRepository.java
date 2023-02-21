package com.containerbooking.repository;

import com.containerbooking.entity.Bookings;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends ReactiveCassandraRepository<Bookings, String> {
}
