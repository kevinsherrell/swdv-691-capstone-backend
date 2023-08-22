package com.dev.mcc_tools.search;

import com.dev.mcc_tools.domain.Appointment;
import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AppointmentSearch {
    private final EntityManager entityManager;

    public Iterable<Appointment> findAllByCriteria(
            AppointmentSearchRequest request
    ) throws ParseException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
        List<Predicate> orPredicates = new ArrayList<>();
        List<Predicate> andPredicates = new ArrayList<>();

        Root<Appointment> root = query.from(Appointment.class);
        Join<Appointment, Profile> profile = root.join("profile");
        Join<Appointment, Order> order = profile.join("orders");

//        if (request.getFirstName() != null) {
//            Predicate fnameP = builder
//                    .like(builder.lower(profile.get("firstName")), ("%" + request.getFirstName() + "%").toLowerCase());
//            predicates.add(
//                    fnameP
//            );
//        }

//        if (request.getLastName() != null) {
//            Predicate lnameP = builder
//                    .like(builder.lower(profile.get("lastName")), ("%" + request.getLastName() + "%").toLowerCase());
//            predicates.add(
//                    lnameP
//            );
//        }

        if ((request.getStatus()) != null) {
            Predicate statusP = builder
                    .like(root.get("status"), request.getStatus());
            orPredicates.add(statusP);
        }
//
        if (request.getLocation() != null) {
            Predicate locationP = builder
                    .equal(order.get("location"), request.getLocation());
            orPredicates.add(locationP);
        }

        if(request.getMinCreationDate() != null){
            Predicate minCreateP = builder
                    .greaterThanOrEqualTo(root.get("date_created"), request.parseDateString(request.getMinCreationDate()));
            orPredicates.add(minCreateP);
        }

        if(request.getMaxCreationDate() != null){
            Predicate maxCreateP = builder
                    .lessThanOrEqualTo(root.get("date_created"), request.parseDateString(request.getMaxCreationDate()));
            orPredicates.add(maxCreateP);
        }

        if (request.getMinDate() != null) {
            Predicate mDateP = builder
                    .greaterThanOrEqualTo(root.get("date"), request.parseDateString(request.getMinDate()));
            orPredicates.add(mDateP);
        }
        if (request.getMaxDate() != null) {

            Predicate maxDateP = builder
                    .lessThanOrEqualTo(root.get("date"), request.parseDateString(request.getMaxDate()));
            orPredicates.add(maxDateP);
        }
        // final query
        query.where(
                builder.or(orPredicates.toArray(new Predicate[0])),
                builder.and(andPredicates.toArray(new Predicate[0]))
        );

        TypedQuery<Appointment> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
