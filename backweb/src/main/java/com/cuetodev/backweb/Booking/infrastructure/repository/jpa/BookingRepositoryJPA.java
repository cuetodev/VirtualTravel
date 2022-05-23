package com.cuetodev.backweb.Booking.infrastructure.repository.jpa;

import com.cuetodev.backweb.Booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepositoryJPA extends JpaRepository<Booking, Integer> {

}
