package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {
    Iterable<Profile> findAll();
    Profile findById(int id);
    Profile findByUserID(int userID);

}