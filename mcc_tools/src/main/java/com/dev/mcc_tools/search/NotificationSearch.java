package com.dev.mcc_tools.search;

import com.dev.mcc_tools.domain.Notification;
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
public class NotificationSearch{

    private final EntityManager entityManager;

    public Iterable<Notification> findAllByCriteria(
            NotificationSearchRequest request
    ) throws ParseException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        List<Predicate> predicates = new ArrayList<>();

        // select from notifications
        Root<Notification> root = criteriaQuery.from(Notification.class);
        if(request.getStatus() != null){
            Predicate statusPredicate = criteriaBuilder
                    .equal(root.get("status"), request.getStatus());
            predicates.add(
                    statusPredicate
            );
        }
        if((request.getProfileID()) != null){
            Predicate profileIDPredicate = criteriaBuilder
                    .equal(root.get("profileID"), String.valueOf(request.getProfileID()));
            predicates.add(profileIDPredicate);
        }
        if(request.getMinDate() != null){
            System.out.println("hopefully correct: " + request.toTimestamp(request.getMinDate()));
            Predicate minDatePredicate = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("date_created"), request.toTimestamp(request.getMinDate()));
            predicates.add(minDatePredicate);
        }
        if(request.getMaxDate() != null){
            System.out.println("coming from max : "  + request.toTimestamp(request.getMaxDate()));
            Predicate maxDatePredicate = criteriaBuilder
                    .lessThanOrEqualTo(root.get("date_created"), request.toTimestamp(request.getMaxDate()));
            predicates.add(maxDatePredicate);

        }
        if(request.getMinTime() != null){
            Predicate minTimePredicate = criteriaBuilder
                    .equal(root.get("date_created"), request.getMinTime());
            predicates.add(minTimePredicate);
        }
        if(request.getMaxTime() != null){
            Predicate maxTimePredicate = criteriaBuilder
                    .equal(root.get("date_created"), request.getMaxTime());
            predicates.add(maxTimePredicate);
        }

        // final query
        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<Notification> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
