package com.cuetodev.backweb.Bus.infrastructure.repository;

import com.cuetodev.backweb.Bus.domain.Bus;
import com.cuetodev.backweb.Bus.infrastructure.repository.jpa.BusRepositoryJPA;
import com.cuetodev.backweb.Bus.infrastructure.repository.port.BusRepositoryPort;
import com.cuetodev.backweb.shared.ErrorHandling.ErrorOutPutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BusRepository implements BusRepositoryPort {

    @Autowired
    BusRepositoryJPA busRepositoryJPA;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Bus> getAvailableBuses(HashMap<String, Object> conditions) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bus> query= cb.createQuery(Bus.class);
        Root<Bus> root = query.from(Bus.class);
        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field,value) ->
        {
            switch (field)
            {
                case "city":
                    predicates.add(cb.like(root.<String>get("city"), (String) value));
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
                        break;
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
        predicates.add(cb.lessThan(root.<Integer>get("occupiedSeats"),41));
        query.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void createUpdateBus(Bus bus) {
        busRepositoryJPA.saveAndFlush(bus);
    }

    @Override
    public Bus findByCityAndDateAndHour(String city, Date date, Float hour) {
        return busRepositoryJPA.findByCityAndDateAndHour(city, date, hour);
    }

    @Override
    public List<Bus> getBookingsByBus(HashMap<String, Object> conditions) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bus> query= cb.createQuery(Bus.class);
        Root<Bus> root = query.from(Bus.class);
        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field,value) ->
        {
            switch (field)
            {
                case "city":
                    predicates.add(cb.like(root.<String>get("city"), (String) value));
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
                        break;
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
            }
        });
        query.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Bus findById(Integer id) {
        Optional<Bus> busOptional = busRepositoryJPA.findById(id);
        return busOptional.orElse(null);
    }
}
