package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private int appointmentID;
    @Column(name = "date_and_time")
    @NotBlank
    private Date dateAndTime;
    @NotBlank
    private String status;
    private String notes;
    @NotBlank
    @Column(name = "order_id")
    private int orderID;
    @NotBlank
    @Column(name = "profile_id")
    private int profileID;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_created;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_updated;

    @OneToMany
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private List<Order> orders;

    @PrePersist
    protected void onCreate(){
        this.date_created = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.date_updated = new Date();
    }

    public Appointment(int appointmentID, Date dateAndTime, String status, String notes, int orderID, int profileID, Date date_created, Date date_updated) {
        this.appointmentID = appointmentID;
        this.dateAndTime = dateAndTime;
        this.status = status;
        this.notes = notes;
        this.orderID = orderID;
        this.profileID = profileID;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Appointment() {
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", dateAndTime=" + dateAndTime +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", orderID=" + orderID +
                ", profileID=" + profileID +
                ", date_created=" + date_created +
                ", date_updated=" + date_updated +
                '}';
    }
}