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
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> datePredicates = new ArrayList<>();

        Root<Appointment> root = query.from(Appointment.class);
        Join<Appointment, Profile> profile = root.join("profile");
        Join<Appointment, Order> order = profile.join("orders");

        if (request.getFirstName() != null) {
            Predicate fnameP = builder
                    .like(builder.lower(profile.get("firstName")), ("%" + request.getFirstName() + "%").toLowerCase());
            predicates.add(
                    fnameP
            );
        }
            if (request.getLastName() != null) {
                Predicate lnameP = builder
                        .like(builder.lower(profile.get("lastName")), ("%" + request.getLastName() + "%").toLowerCase());
                predicates.add(
                        lnameP
                );
            }
            if ((request.getStatus()) != null) {
                Predicate statusP = builder
                        .equal(root.get("status"), request.getStatus());
                predicates.add(statusP);
            }
//
            if (request.getInvoiceNumber() != null) {
                Predicate invoiceP = builder
                        .like(order.get("invoiceNumber"), "%" + request.getInvoiceNumber() + "%");
                predicates.add(invoiceP);
            }

            if (request.getMinDate() != null) {
                Predicate mDateP = builder
                        .greaterThanOrEqualTo(root.get("date_created"), request.parseDateString(request.getMinDate()));
                datePredicates.add(mDateP);
            }
            if (request.getMaxDate() != null) {

                Predicate maxDateP = builder
                        .lessThanOrEqualTo(root.get("date_created"), request.parseDateString(request.getMaxDate()));
                datePredicates.add(maxDateP);
            }
            // final query
            query.where(
                    builder.or(predicates.toArray(new Predicate[0])),
                    builder.and(datePredicates.toArray(new Predicate[0]))
            );

            TypedQuery<Appointment> typedQuery = entityManager.createQuery(query);
            return typedQuery.getResultList();
        }
}
