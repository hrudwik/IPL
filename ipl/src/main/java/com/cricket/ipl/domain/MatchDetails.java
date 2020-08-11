package com.cricket.ipl.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class MatchDetails implements Serializable {

    private Integer matchId;
    private String teamName1;
    private String teamName2;
    private Timestamp scheduleDate;
    private List<String> players;

    public MatchDetails() {
    }

    public MatchDetails(Integer matchId, String teamName1, String teamName2, Timestamp scheduleDate, List<String> players) {
        this.matchId = matchId;
        this.teamName1 = teamName1;
        this.teamName2 = teamName2;
        this.scheduleDate = scheduleDate;
        this.players = players;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getTeamName1() {
        return teamName1;
    }

    public void setTeamName1(String teamName1) {
        this.teamName1 = teamName1;
    }

    public String getTeamName2() {
        return teamName2;
    }

    public void setTeamName2(String teamName2) {
        this.teamName2 = teamName2;
    }

    public Timestamp getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Timestamp scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "MatchDetails{" +
                "matchId=" + matchId +
                ", teamName1='" + teamName1 + '\'' +
                ", teamName2='" + teamName2 + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", players=" + players +
                '}';
    }
}
