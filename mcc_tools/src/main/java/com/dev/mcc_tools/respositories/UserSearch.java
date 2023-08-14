package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.Notification;
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
public class UserSearch {

    private final EntityManager entityManager;

    public Iterable<User> findAllByCriteria(
            UserSearchRequest request
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<User> root = criteriaQuery.from(User.class);

        if (request.getRoleID() != null) {
            System.out.println(request.getRoleID());
            Predicate roleIDP = criteriaBuilder
                    .equal(root.get("roleID"), request.getRoleID());
            predicates.add(
                    roleIDP
            );
        }
        if (request.getEmail() != null) {
            Predicate emailP = criteriaBuilder
                    .like(root.get("email"), "%" + request.getEmail() + "%");
            predicates.add(emailP);
        }


        // final query
        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
