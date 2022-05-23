package com.cuetodev.backempresa.Booking.infrastructure.repository;

import com.cuetodev.backempresa.Booking.domain.Booking;
import com.cuetodev.backempresa.Booking.infrastructure.repository.jpa.BookingRepositoryJPA;
import com.cuetodev.backempresa.Booking.infrastructure.repository.port.BookingRepositoryPort;
import com.cuetodev.backempresa.shared.ErrorHandling.ErrorOutPutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookingRepository implements BookingRepositoryPort {

    @Autowired
    private BookingRepositoryJPA bookingRepositoryJPA;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepositoryJPA.save(booking);
    }

    @Override
    public List<Booking> getBookingsByConditions(HashMap<String, Object> conditions) throws ErrorOutPutDTO {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query= cb.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);
        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field,value) ->
        {
            switch (field)
            {
                case "city":
                    if (!value.equals("noCity")) {
                        predicates.add(cb.like(root.<String>get("city"), (String) value));
                    }
                    break;
                case "lowerDate":
                    Date lowerDate;
                    try {
                        lowerDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) value);
                    } catch (ParseException e) {
                        throw new ErrorOutPutDTO(400, "Invalid date format", "Fatal");
                    }
                    predicates.add(cb.greaterThan(root.<Date>get("date"),lowerDate));
                    break;
                case "upperDate":
                    if (!value.equals("noDate")) {
                        Date upperDate;
                        try {
                            upperDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) value);
                        } catch (ParseException e) {
                            throw new ErrorOutPutDTO(400, "Invalid date format", "Fatal");
                        }
                        predicates.add(cb.lessThan(root.<Date>get("date"),upperDate));
                    }
                    break;
                case "lowerHour":
                    if ((Float) value != -1) {
                        predicates.add(cb.greaterThan(root.<Float>get("hour"),(Float)value));
                    }
                    break;
                case "upperHour":
                    if ((Float) value != -1) {
                        predicates.add(cb.lessThan(root.<Float>get("hour"),(Float)value));
                    }
                    break;
                case "email":
                    predicates.add(cb.like(root.get("email"), (String) value));
                    break;
                case "equalDate":
                    Date equalDate;
                    try {
                        equalDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) value);
                    } catch (ParseException e) {
                        throw new ErrorOutPutDTO(400, "Invalid date format", "Fatal");
                    }
                    predicates.add(cb.equal(root.get("date"), equalDate));
                    break;
                case "equalHour":
                    predicates.add(cb.le(root.<Float>get("hour"),(Float)value));
                    break;
            }
        });
        query.select(root).where(predicates.toArray(new Predicate[0]));

        List<Booking> bookings = entityManager.createQuery(query).getResultList();

        if (bookings.isEmpty()) throw new ErrorOutPutDTO(404, "Booking not found", "Fatal");

        return bookings;
    }

    @Override
    public Booking findById(Integer id) {
        Optional<Booking> bookingOptional = bookingRepositoryJPA.findById(id);
        return bookingOptional.orElse(null);
    }
}
