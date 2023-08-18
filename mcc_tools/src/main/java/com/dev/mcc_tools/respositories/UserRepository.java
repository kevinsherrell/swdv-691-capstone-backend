package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Iterable<User> findAll();
    User findById(int id);
    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

}