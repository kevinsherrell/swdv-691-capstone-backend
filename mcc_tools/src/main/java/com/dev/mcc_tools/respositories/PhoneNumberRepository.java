package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Integer> {
    Iterable<PhoneNumber> findAll();
    PhoneNumber findById(int id);
    PhoneNumber findByPhoneID(int phoneID);
}