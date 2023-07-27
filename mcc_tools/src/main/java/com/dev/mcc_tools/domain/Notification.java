package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Table(name = "notifications")
public class Notification{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int notificationID;
    @NotBlank
    private String body;
    @NotBlank
    private String status;

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

    public Notification(int notificationID, String body, String status, int profileID, Date date_created, Date date_updated) {
        this.notificationID = notificationID;
        this.body = body;
        this.status = status;
        this.profileID = profileID;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Notification() {
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}