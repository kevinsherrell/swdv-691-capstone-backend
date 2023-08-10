package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    private Boolean isPrimary;
    @NotNull
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

    public PhoneNumber(int phoneID, String number, String phoneType, Boolean isPrimary,int profileID, Date date_created, Date date_updated) {
        this.phoneID = phoneID;
        this.number = number;
        this.phoneType = phoneType;
        this.isPrimary = isPrimary;
        this.profileID = profileID;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public PhoneNumber(int phoneID, String number, String phoneType, Boolean isPrimary,int profileID) {
        this.phoneID = phoneID;
        this.number = number;
        this.phoneType = phoneType;
        this.isPrimary = isPrimary;
        this.profileID = profileID;
    }

    public PhoneNumber() {
    }

    public int getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(int phoneID) {
        this.phoneID = phoneID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(Date date_updated) {
        this.date_updated = date_updated;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "phoneID=" + phoneID +
                ", number='" + number + '\'' +
                ", phoneType='" + phoneType + '\'' +
                ", isPrimary=" + isPrimary +
                ", profileID=" + profileID +
                ", date_created=" + date_created +
                ", date_updated=" + date_updated +
                '}';
    }
}