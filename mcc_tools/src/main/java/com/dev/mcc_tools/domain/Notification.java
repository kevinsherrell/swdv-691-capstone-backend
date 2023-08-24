package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;


@Entity
@Table(name = "notifications")
@Getter
@Setter
@RequiredArgsConstructor
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "America/Chicago")
    private Date date_created;
    @UpdateTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "America/Chicago")
    private Date date_updated;


    public Notification(int notificationID, String header, String body, String status, int profileID) {
        this.notificationID = notificationID;
        this.header = header;
        this.body = body;
        this.status = status;
        this.profileID = profileID;
    }

}