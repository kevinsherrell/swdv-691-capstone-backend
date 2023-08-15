package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.Address;
import com.dev.mcc_tools.domain.PhoneNumber;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    Iterable<Address> findAll();
    Address findByAddressID(int addressID);
}