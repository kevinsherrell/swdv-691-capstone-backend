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


@Entity
@Table(name = "orders")
@Getter
@Setter
@RequiredArgsConstructor
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderID;

    @NotBlank
    @Column(name = "invoice_number")
    private String invoiceNumber;

    @NotBlank
    private String status;

    @NotBlank
    private String location;

    @NotNull
    @Column(name = "profile_id")
    private int profileID;

    @CreationTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_created;

    @UpdateTimestamp(source = SourceType.DB)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private Date date_updated;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
    @JsonIgnore
    private Profile profile;
}