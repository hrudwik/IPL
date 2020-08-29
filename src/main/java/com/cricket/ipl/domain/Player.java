package com.cricket.ipl.domain;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Hrudwik Dhulipalla
 */
@Entity
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(required = true, column = "playerId")
    private Integer playerId;
    @CsvBindByName(required = true, column = "playerName")
    private String playerName;
    @CsvBindByName(required = true, column = "teamName")
    private String teamName;

    public Player() {
    }

    public Player(Integer playerId, String playerName, String teamName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.teamName = teamName;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
