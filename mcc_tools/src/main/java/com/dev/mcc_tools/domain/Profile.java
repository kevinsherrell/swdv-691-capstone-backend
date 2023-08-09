package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "profiles")
public class Profile{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private int profileID;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @Column(name="middle_initial")
    private String middleInitial;
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @Column(name = "user_id")
    private int userID;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    private PhoneNumber phoneNumber;
    @OneToMany
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    private List<Order> orders;
    @OneToMany
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    private List<Appointment> appointments;
    @OneToMany
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    private List<Address> addresses;
    @OneToMany
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    private List<Notification> notifications;

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

    public Profile(int profileID, String firstName, String middleInitial, String lastName, int userID, Date date_created, Date date_updated) {
        this.profileID = profileID;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.userID = userID;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Profile(int profileID, String firstName, String lastName, int userID) {
        this.profileID = profileID;
        this.firstName = firstName;
        this.middleInitial = "none";
        this.lastName = lastName;
        this.userID = userID;

    }

    public Profile() {
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileID=" + profileID +
                ", firstName='" + firstName + '\'' +
                ", middleInitial='" + middleInitial + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userID=" + userID +
                ", date_created=" + date_created +
                ", date_updated=" + date_updated +
                '}';
    }
}