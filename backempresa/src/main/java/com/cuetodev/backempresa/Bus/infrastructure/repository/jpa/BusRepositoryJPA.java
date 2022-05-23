package com.cuetodev.backempresa.Bus.infrastructure.repository.jpa;

import com.cuetodev.backempresa.Bus.domain.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BusRepositoryJPA extends JpaRepository<Bus, Integer> {
    public Bus findByCityAndDateAndHour(String city, Date date, Float hour);
}
