package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;


@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int notificationID;
    @NotBlank
    private String header;
    @NotBlank
    private String body;
    @NotBlank
    private String status;

    @Column(name = "profile_id")
    private int profileID;

    @CreationTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    private Instant date_created;
    @UpdateTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    private Instant date_updated;

    public Notification(String header, String body, String status, int profileID) {
        this.header = header;
        this.body = body;
        this.status = status;
        this.profileID = profileID;
    }


//    @PrePersist
//    protected void onCreate() {
//        this.date_created = new Date();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.date_updated = new Date();
//    }

    public Notification(int notificationID, String header, String body, String status, int profileID) {
        this.notificationID = notificationID;
        this.header = header;
        this.body = body;
        this.status = status;
        this.profileID = profileID;
    }
    public Notification() {

    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public Instant getDate_created() {
        return date_created;
    }

    public void setDate_created(Instant date_created) {
        this.date_created = date_created;
    }

    public Instant getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(Instant date_updated) {
        this.date_updated = date_updated;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                ", status='" + status + '\'' +
                ", profileID=" + profileID +
                ", date_created=" + date_created +
                ", date_updated=" + date_updated +
                '}';
    }
}