package com.camel.api.user.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "DELETED_AT")
    private Date deletedAt;
}
