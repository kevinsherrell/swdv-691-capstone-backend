package com.dev.mcc_tools.search;

import com.dev.mcc_tools.domain.Order;
import com.dev.mcc_tools.domain.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSearch {
    private final EntityManager entityManager;

    public Iterable<Order> findAllByCriteria(
            OrderSearchRequest request
    ) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Order> root = query.from(Order.class);
        Join<Order, Profile> profile = root.join("profile");
//        Join<Order, User> user = root.join("user");

        if (request.getFirstName() != null) {
            Predicate fnameP = builder
                    .like(builder.lower(profile.get("firstName")), ("%" + request.getFirstName() + "%").toLowerCase());
            predicates.add(
                    fnameP
            );
        }
//
//        if ((request.getLastName()) != null) {
//            Predicate lnameP = builder
//                    .like(root.get("lastName"), "%" + request.getLastName() + "%");
//            predicates.add(lnameP);
//        }
//
//        if (request.getPhoneNumber() != null) {
//            Predicate phoneP = builder
//                    .like(phone.get("number"), "%" + request.getPhoneNumber() + "%");
//            predicates.add(phoneP);
//        }
//
//        if (request.getEmail() != null) {
//            Predicate emailP = builder
//                    .like(user.get("email"), "%" + request.getEmail() + "%");
//            predicates.add(emailP);
//        }


        // final query
        query.where(
                builder.or(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
