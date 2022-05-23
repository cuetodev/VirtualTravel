package com.cuetodev.backweb.Bus.infrastructure.repository.port;


import com.cuetodev.backweb.Bus.domain.Bus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface BusRepositoryPort {
    public List<Bus> getAvailableBuses(HashMap<String, Object> conditions);
    public void createUpdateBus(Bus bus);
    public List<Bus> getBookingsByBus(HashMap<String, Object> conditions);
    public Bus findById(Integer id);
    public Bus findByCityAndDateAndHour(String city, Date date, Float hour);
}
