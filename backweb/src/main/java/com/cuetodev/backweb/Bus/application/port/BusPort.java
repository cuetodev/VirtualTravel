package com.cuetodev.backweb.Bus.application.port;

import com.cuetodev.backweb.Bus.domain.Bus;
import com.cuetodev.backweb.Bus.infrastructure.controller.dto.output.BookingsByBusOutputDTO;
import com.cuetodev.backweb.Bus.infrastructure.controller.dto.output.BusAvailabilityOutputDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface BusPort {
    public List<BusAvailabilityOutputDTO> getAvailableBuses(HashMap<String, Object> conditions);
    public void createUpdateBus(Bus bus);
    public List<BookingsByBusOutputDTO> getBookingsByBus(HashMap<String, Object> conditions);
    public Bus findById(Integer id);
}
