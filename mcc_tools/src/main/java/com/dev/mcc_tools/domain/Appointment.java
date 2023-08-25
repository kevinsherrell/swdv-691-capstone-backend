package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "appointment_date")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date appointmentDate;

    @NotBlank
    private String status;

    private String notes;

    @NotNull
    @Column(name = "order_id")
    private int orderID;

    @NotNull
    @Column(name = "profile_id")
    private int profileID;

    @Column(name = "date_created")
    @CreationTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateCreated;

    @Column(name = "date_updated")
    @UpdateTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateUpdated;


    @OneToMany
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
//    @JsonIgnore
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    @JsonIgnore
    private Profile profile;
}