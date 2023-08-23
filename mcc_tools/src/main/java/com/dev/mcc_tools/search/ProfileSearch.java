package com.dev.mcc_tools.search;

import com.dev.mcc_tools.domain.Notification;
//import com.dev.mcc_tools.domain.PhoneNumber;
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
public class ProfileSearch {

    private final EntityManager entityManager;

    public Iterable<Profile> findAllByCriteria(
            ProfileSearchRequest request
    ) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Profile> query = builder.createQuery(Profile.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Profile> root = query.from(Profile.class);

        Join<Profile, User> user = root.join("user");

        if (request.getFirstName() != null) {
            Predicate fnameP = builder
                    .like(builder.lower(root.get("firstName")), ("%" + request.getFirstName() + "%").toLowerCase());
            predicates.add(
                    fnameP
            );
        }

        if (request.getLastName() != null) {
            Predicate lnameP = builder
                    .like(builder.lower(root.get("lastName")), ("%" + request.getLastName() + "%").toLowerCase());
            predicates.add(lnameP);
        }


        if (request.getEmail() != null) {
            Predicate emailP = builder
                    .like(builder.lower(user.get("email")), ("%" + request.getEmail() +"%").toLowerCase() );
            predicates.add(emailP);
        }

        if(request.getPhoneNumber() != null){
            Predicate phoneP = builder
                    .like(root.get("phoneNumber"), "%" + request.getPhoneNumber() + "%");
            predicates.add(phoneP);
        }

        // final query
        query.where(
                builder.or(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<Profile> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
