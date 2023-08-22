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

        // phone number join ... and possibly the user join,  below caused issue that prevented data from being retrieved properly.
        // Explore further...
//        Join<Profile, PhoneNumber> phone = root.join("phoneNumber");
        Join<Profile, User> user = root.join("user");

        if (request.getFirstName() != null) {
            Predicate fnameP = builder
                    .like(builder.lower(root.get("firstName")), ("%" + request.getFirstName() + "%").toLowerCase());
            predicates.add(
                    fnameP
            );
        }

        if ((request.getLastName()) != null) {
            Predicate lnameP = builder
                    .like(root.get("lastName"), "%" + request.getLastName() + "%");
            predicates.add(lnameP);
        }

//        if (request.getPhoneNumber() != null) {
//            Predicate phoneP = builder
//                    .like(phone.get("number"), "%" + request.getPhoneNumber() + "%");
//            predicates.add(phoneP);
//        }

        if (request.getEmail() != null) {
            Predicate emailP = builder
                    .like(user.get("email"), "%" + request.getEmail() +"%" );
            predicates.add(emailP);
        }


        // final query
        query.where(
                builder.or(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<Profile> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
