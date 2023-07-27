package com.dev.mcc_tools.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;


@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleID;
    @NotBlank
    private String type;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    @JsonIgnore
    private Date date_created;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    @JsonIgnore
    private Date date_updated;

    @PrePersist
    protected void onCreate() {
        this.date_created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.date_updated = new Date();
    }

    public Role(int roleID, String type, Date date_created, Date date_updated) {
        this.roleID = roleID;
        this.type = type;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Role() {
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "Role{" +
                "roleID=" + roleID +
                ", type='" + type + '\'' +
                ", date_created=" + date_created +
                ", date_updated=" + date_updated +
                '}';
    }
}