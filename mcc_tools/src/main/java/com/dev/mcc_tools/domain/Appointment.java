package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private int appointmentID;

    @NotNull

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    @NotBlank
    private String status;

    private String notes;

    @NotNull
    @Column(name = "order_id")
    private int orderID;

    @NotNull
    @Column(name = "profile_id")
    private int profileID;

    @CreationTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date_created;

    @UpdateTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date_updated;


    @OneToMany
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
//    @JsonIgnore
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    @JsonIgnore
    private Profile profile;
}