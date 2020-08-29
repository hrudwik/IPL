package com.cricket.ipl.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Hrudwik Dhulipalla
 */
@Entity
public class UserScorecard implements Serializable {

    private Integer userId;
    private String emailId;
    private String userName;
    private Integer points;
    private Integer money;

    public UserScorecard() {
    }

    public UserScorecard(Integer userId, String emailId, String userName, Integer points, Integer money) {
        this.userId = userId;
        this.emailId = emailId;
        this.userName = userName;
        this.points = points;
        this.money = money;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "UserScorecard{" +
                "userId=" + userId +
                ", emailId='" + emailId + '\'' +
                ", userName='" + userName + '\'' +
                ", points=" + points +
                ", money=" + money +
                '}';
    }
}
