package com.cuetodev.backweb.Booking.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotNull
    private String city;

    @NotNull
    private String name;

    private String surname;

    private String phone;

    @NotNull
    private String email;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    private Float hour;

    private String status;
}
