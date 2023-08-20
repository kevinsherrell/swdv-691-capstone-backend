package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "profiles")
@Getter
@Setter
@RequiredArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private int profileID;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_initial")
    private String middleInitial;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "user_id", unique = true)
    private int userID;

    @CreationTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_created;

    @UpdateTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_updated;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
//    @JsonIgnore
    private PhoneNumber phoneNumber;
    @OneToMany
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
//    @JsonIgnore
    private List<Order> orders;
    @OneToMany
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
//    @JsonIgnore
    private List<Appointment> appointments;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    private Address address;
    @OneToMany
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    private List<Notification> notifications;


}