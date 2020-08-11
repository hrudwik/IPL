package com.cricket.ipl.dao;

import com.cricket.ipl.domain.Player;

import java.time.LocalDate;
import java.util.List;

public interface PlayerDao {
    void insertPlayer(Player player);

    Player selectPlayerById(Integer playerId);

    Player selectPlayerByName(String playerName);

    List<Player> selectAllPlayers();

    List<Player> selectAllPlayersByTeamName(String teamName);

    List<Player> selectAllPlayersByTeamNames(String teamName1, String teamName2);

    void deletePlayer(Integer playerId);
}
