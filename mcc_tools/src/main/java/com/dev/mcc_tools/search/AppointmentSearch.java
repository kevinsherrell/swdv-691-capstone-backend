package com.dev.mcc_tools.search;

import com.dev.mcc_tools.controllers.FormattedResponse;
import com.dev.mcc_tools.domain.Appointment;
import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
        Root<Appointment> root = query.from(Appointment.class);

        List<Predicate> statusAndInvoicePredicates = new ArrayList<>();
        // contains predicates for dates
        List<Predicate> datePredicates = new ArrayList<>();
        // contains predicates for creation dates
        List<Predicate> creationPredicates = new ArrayList<>();
        // combines date predicates with AND clause;
//        Predicate betweenDates = null;
        // combines creation date predicates with AND clause;
//        Predicate betweenCreationDates = null;


//        Join<Appointment, Profile> profile = root.join("profile");
//        Join<Appointment, Order> order = profile.join("orders");

        if ((request.getStatus()) != null) {
            Predicate statusP = builder
                    .equal(root.get("status"), request.getStatus());
            statusAndInvoicePredicates.add(statusP);
        }
        if (request.getMinCreationDate() != null) {
            Predicate minCreateP = builder
                    .greaterThanOrEqualTo(root.get("date_created"), request.parseDateString(request.getMinCreationDate()));
            creationPredicates.add(minCreateP);
        }

        if (request.getMaxCreationDate() != null) {
            Predicate maxCreateP = builder
                    .lessThanOrEqualTo(root.get("date_created"), request.parseDateString(request.getMaxCreationDate()));
            creationPredicates.add(maxCreateP);
        }

        if (request.getMinDate() != null && request.getMaxDate() != null) {
            Predicate betweenDates = builder.and(
                    builder.greaterThanOrEqualTo(
                            root.get("date"),
                            request.parseDateString(request.getMinDate())
                    ),
                    builder.lessThanOrEqualTo(
                            root.get("date"),
                            request.parseDateString(request.getMaxDate())
                    ));
            datePredicates.add(betweenDates);
        }
        if (request.getMaxCreationDate() != null && request.getMinCreationDate() != null) {
            Predicate betweenCreationDates = builder.and(
                    builder.greaterThanOrEqualTo(
                            root.get("date_created"), request.parseDateString(request.getMaxCreationDate())

                    ),
                    builder.lessThanOrEqualTo(root.get("date_created"), request.parseDateString(request.getMaxCreationDate()))
            );
            creationPredicates.add(betweenCreationDates);
        }
        // final query
        query.where(
                builder.or(builder.equal(root.get("status"), request.getStatus())),
                builder.and(datePredicates.toArray(new Predicate[0])),
                builder.and(creationPredicates.toArray(new Predicate[0])
        ));

        TypedQuery<Appointment> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
//    Iterable<Appointment> appointmentsForMonth(Timestamp date) {
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
//        Root<Appointment> root = query.from(Appointment.class);
//
//        query.where(
//                builder.equal(builder.)
//        )
//        TypedQuery<Appointment> typedQuery = entityManager.createQuery(query);
//        return typedQuery.getResultList();
//
//    }
}
