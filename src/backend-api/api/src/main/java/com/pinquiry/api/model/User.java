package com.pinquiry.api.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;


    private String user_password;


    private String email;



    private Timestamp signupDate;

    @Column(columnDefinition = "text[]")
    @Type(type="com.pinquiry.api.model.CustomStringArrayType")
    private String[] Monitors;



    public User() {
    }

    public User(String username, String user_password, String email, Timestamp signupDate) {
        this.username = username;
        this.user_password = user_password;
        this.email = email;
        this.signupDate = signupDate;
    }


    public Long getId() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String password) {
        this.user_password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getMonitors() {
        return Monitors;
    }

    public void setMonitors(String[] monitors) {
        Monitors = monitors;
    }

    public Timestamp getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Timestamp signupDate) {
        this.signupDate = signupDate;
    }

    public void setId(Long id) {
        this.user_id = id;
    }


}

