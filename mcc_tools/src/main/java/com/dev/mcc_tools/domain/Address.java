//package com.dev.mcc_tools.domain;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.SourceType;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.util.Date;
//
//
//@Entity
//@Table(name = "addresses")
//@Getter
//@Setter
//@RequiredArgsConstructor
//public class Address {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "address_id")
//    private int addressID;
//
//    @Column(name = "address_1")
//    @NotBlank
//    private String address1;
//
//    @Column(name = "address_2")
//    private String address2;
//
//    @NotBlank
//    private String city;
//
//    @NotBlank
//    private String state;
//
//    @Column(name = "zip_code")
//    @NotBlank
//    private String zipCode;
//
//    @Column(name = "profile_id", unique = true)
//    @NotNull
//    private int profileID;
//
//    @CreationTimestamp(source = SourceType.DB)
//    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
//    private Date date_created;
//
//    @UpdateTimestamp(source = SourceType.DB)
//    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
//    private Date date_updated;
//}