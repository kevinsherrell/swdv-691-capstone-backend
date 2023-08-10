package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;


@Entity
@Table(name = "phone_numbers")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private int phoneID;
    @NotBlank
    private String number;
    @NotBlank
    @Column(name = "phone_type")
    private String phoneType;
    @NotBlank
    @Column(name = "primary")
    private Boolean primary;
    @NotBlank
    @Column(name = "profile_id")
    private int profileID;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_created;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_updated;


    @PrePersist
    protected void onCreate() {
        this.date_created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.date_updated = new Date();
    }

    public PhoneNumber(int phoneID, String number, String phoneType, Boolean primary,int profileID, Date date_created, Date date_updated) {
        this.phoneID = phoneID;
        this.number = number;
        this.phoneType = phoneType;
        this.primary = primary;
        this.profileID = profileID;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public PhoneNumber(int phoneID, String number, String phoneType, Boolean primary,int profileID) {
        this.phoneID = phoneID;
        this.number = number;
        this.phoneType = phoneType;
        this.primary = primary;
        this.profileID = profileID;
    }

    public PhoneNumber() {
    }

}