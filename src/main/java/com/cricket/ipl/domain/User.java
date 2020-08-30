package com.cricket.ipl.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Hrudwik Dhulipalla
 */
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String emailId;
    private String userName;
    private String phoneNumber;
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    private String token;

    public User() {
        super();
        this.enabled=false;
    }

    public User(Integer id, String userName, String emailId, String password, boolean enabled) {
        this.id = id;
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
        this.enabled = enabled;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Integer getId() {
        return id;
    }

    public String getEmailId() { return emailId; }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emailId='" + emailId + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", token='" + token + '\'' +
                '}';
    }
}
