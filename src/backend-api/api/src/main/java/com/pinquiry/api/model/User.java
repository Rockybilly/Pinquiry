package com.pinquiry.api.model;

import com.pinquiry.api.model.monitor.Monitor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    public enum UserRole {
        ADMIN, USER
    }

    private String username;
    @Column(name = "user_password")
    private String password;
    private String email;
    private Timestamp signupDate;
    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy="monUser")
    private List<Monitor> monitors;

    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }

    public User() {
    }

    public User(String username, String user_password, String email, Timestamp signupDate) {
        this.username = username;
        this.password = user_password;
        this.email = email;
        this.signupDate = signupDate;
    }


    public Long getId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Timestamp signupDate) {
        this.signupDate = signupDate;
    }

    public void setId(Long id) {
        this.userId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


}

