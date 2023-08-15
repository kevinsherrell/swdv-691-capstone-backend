package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
@Table(name = "orders")
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



    public Order(int orderID, String invoiceNumber, String status, String location, int profileID, Date date_created, Date date_updated) {
        this.orderID = orderID;
        this.invoiceNumber = invoiceNumber;
        this.status = status;
        this.location = location;
        this.profileID = profileID;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Order() {
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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