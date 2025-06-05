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
    // 본 서비스에서 사용자를 구분하는 유일한 식별자
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USERNAME")
    private String userName;

    // SSO 로그인할 경우, IdP에서 제공하는 사용자 ID값
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "DELETED_AT")
    private Date deletedAt;
}
