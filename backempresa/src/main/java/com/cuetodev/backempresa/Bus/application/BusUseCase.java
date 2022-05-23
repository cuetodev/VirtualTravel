package com.cuetodev.backempresa.Bus.application;

import com.cuetodev.backempresa.Bus.application.port.BusPort;
import com.cuetodev.backempresa.Bus.domain.Bus;
import com.cuetodev.backempresa.Bus.infrastructure.controller.dto.output.BookingsByBusOutputDTO;
import com.cuetodev.backempresa.Bus.infrastructure.controller.dto.output.BusAvailabilityOutputDTO;
import com.cuetodev.backempresa.Bus.infrastructure.repository.port.BusRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class BusUseCase implements BusPort {

    @Autowired
    BusRepositoryPort busRepositoryPort;

    @Override
    public List<BusAvailabilityOutputDTO> getAvailableBuses(HashMap<String, Object> conditions) {
        List<Bus> busesReceived;
        busesReceived = busRepositoryPort.getAvailableBuses(conditions);

        List<BusAvailabilityOutputDTO> busAvailabilityOutputDTOS = new ArrayList<>();
        busesReceived.forEach(bus -> busAvailabilityOutputDTOS.add(new BusAvailabilityOutputDTO(bus)));

        return busAvailabilityOutputDTOS;
    }

    @Override
    public void createUpdateBus(Bus bus) {
        busRepositoryPort.createUpdateBus(bus);
    }

    @Override
    public List<BookingsByBusOutputDTO> getBookingsByBus(HashMap<String, Object> conditions) {
        List<Bus> busesReceived;
        busesReceived = busRepositoryPort.getBookingsByBus(conditions);

        List<BookingsByBusOutputDTO> bookingsByBusOutputDTOS = new ArrayList<>();
        busesReceived.forEach(bus -> bookingsByBusOutputDTOS.add(new BookingsByBusOutputDTO(bus)));

        return bookingsByBusOutputDTOS;
    }
}
