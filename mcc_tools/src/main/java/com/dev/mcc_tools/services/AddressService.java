//package com.dev.mcc_tools.services;
//
//import com.dev.mcc_tools.domain.Address;
//import com.dev.mcc_tools.respositories.AddressRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AddressService {
//    @Autowired
//    private AddressRepository addressRepository;
//
//    public Address saveOrUpdateAddress(Address address) {
//        try {
//            return addressRepository.save(address);
//        } catch (Exception e) {
//            e.getCause();
//        }
//        return null;
//    }
//
//    public Iterable<Address> findAllAddresses() {
//        return addressRepository.findAll();
//    }
//
//
//    public Address findByAddressID(int addressID) {
//        return addressRepository.findByAddressID(addressID);
//    }
//
//
//};