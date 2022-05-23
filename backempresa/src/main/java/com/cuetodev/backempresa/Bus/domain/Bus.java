package com.cuetodev.backempresa.Bus.domain;

import com.cuetodev.backempresa.Booking.domain.Booking;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotNull
    private boolean active;

    @NotNull
    private String city;

    @NotNull
    private int occupiedSeats;

    @NotNull
    private Float hour;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "booking_id")
    private Set<Booking> bookingList;

    public Bus (Integer id, boolean active, String city, Float hour, Date date, Set<Booking> bookingList) {
        this.id = id;
        this.active = active;
        this.city = city;
        this.hour = hour;
        this.date = date;
        this.bookingList = bookingList;
        this.occupiedSeats = bookingList.size();
    }

}
