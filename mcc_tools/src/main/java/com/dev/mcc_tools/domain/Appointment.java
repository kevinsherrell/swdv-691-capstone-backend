package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

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

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_created;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_updated;

    @PrePersist
    protected void onCreate(){
        this.date_created = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.date_updated = new Date();
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
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", dateAndTime=" + dateAndTime +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", date_created=" + date_created +
                ", date_updated=" + date_updated +
                '}';
    }
}