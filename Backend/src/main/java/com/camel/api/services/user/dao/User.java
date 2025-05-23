package com.camel.api.services.user.dao;

import java.util.Date;

import com.camel.common.CustomMap;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public User(){}

    public User(CustomMap user) {
        ObjectMapper mapper = new ObjectMapper();
        User converted = mapper.convertValue(user, User.class);

        this.id = converted.id;
        this.userName = converted.userName;
        this.userId = converted.userId;
        this.provider = converted.provider;
        this.createdAt = converted.createdAt;
        this.deletedAt = converted.deletedAt;
    }


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
