package com.cricket.ipl.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class UserPrediction implements Serializable {

    private String emailId;
    private Integer matchId;
    private String bestBatsmen;
    private String bestBowler;
    private String manOfTheMatch;
    private String winner;

    public UserPrediction() {
    }

    public UserPrediction(String emailId, Integer matchId, String bestBatsmen, String bestBowler, String manOfTheMatch, String winner) {
        this.emailId = emailId;
        this.matchId = matchId;
        this.bestBatsmen = bestBatsmen;
        this.bestBowler = bestBowler;
        this.manOfTheMatch = manOfTheMatch;
        this.winner = winner;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getBestBatsmen() {
        return bestBatsmen;
    }

    public void setBestBatsmen(String bestBatsmen) {
        this.bestBatsmen = bestBatsmen;
    }

    public String getBestBowler() {
        return bestBowler;
    }

    public void setBestBowler(String bestBowler) {
        this.bestBowler = bestBowler;
    }

    public String getManOfTheMatch() {
        return manOfTheMatch;
    }

    public void setManOfTheMatch(String manOfTheMatch) {
        this.manOfTheMatch = manOfTheMatch;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "UserPrediction{" +
                "emailId='" + emailId + '\'' +
                ", matchId=" + matchId +
                ", bestBatsmen='" + bestBatsmen + '\'' +
                ", bestBowler='" + bestBowler + '\'' +
                ", manOfTheMatch='" + manOfTheMatch + '\'' +
                ", winner='" + winner + '\'' +
                '}';
    }
}
