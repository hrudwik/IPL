package com.cricket.ipl.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author Hrudwik Dhulipalla
 */
@Entity
public class MatchSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchId;
    private String teamName1;
    private String teamName2;
    private Timestamp scheduleDate;
    private String bestBatsmen;
    private String bestBowler;
    private String manOfTheMatch;
    private String winner;

    public MatchSchedule() {
    }

    public MatchSchedule(Integer matchId, String teamName1, String teamName2, Timestamp scheduleDate, String bestBatsmen, String bestBowler, String manOfTheMatch, String winner) {
        this.matchId = matchId;
        this.teamName1 = teamName1;
        this.teamName2 = teamName2;
        this.scheduleDate = scheduleDate;
        this.bestBatsmen = bestBatsmen;
        this.bestBowler = bestBowler;
        this.manOfTheMatch = manOfTheMatch;
        this.winner = winner;
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
        return "MatchSchedule{" +
                "matchId=" + matchId +
                ", teamName1='" + teamName1 + '\'' +
                ", teamName2='" + teamName2 + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", bestBatsmen='" + bestBatsmen + '\'' +
                ", bestBowler='" + bestBowler + '\'' +
                ", manOfTheMatch='" + manOfTheMatch + '\'' +
                ", winner='" + winner + '\'' +
                '}';
    }
}
