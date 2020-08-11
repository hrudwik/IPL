package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.PlayerDao;
import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.dao.mybatis.mapper.PlayerMapper;
import com.cricket.ipl.dao.mybatis.mapper.UserMapper;
import com.cricket.ipl.domain.Player;
import com.cricket.ipl.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("playerDaoImpl")
public class PlayerDaoImpl implements PlayerDao {

    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public void insertPlayer(Player player) {
        playerMapper.insertPlayer(player);
    }

    @Override
    public Player selectPlayerById(Integer playerId) {
        return playerMapper.selectPlayerById(playerId);
    }

    @Override
    public Player selectPlayerByName(String playerName) {
        return playerMapper.selectPlayerByName(playerName);
    }

    @Override
    public List<Player> selectAllPlayers() {
        return playerMapper.selectAllPlayers();
    }

    @Override
    public List<Player> selectAllPlayersByTeamName(String teamName) {
        return playerMapper.selectAllPlayersByTeamName(teamName);
    }

    @Override
    public List<Player> selectAllPlayersByTeamNames(String teamName1, String teamName2) {
        return playerMapper.selectAllPlayersByTeamNames(teamName1, teamName2);
    }

    @Override
    public void deletePlayer(Integer playerId) {
        playerMapper.deletePlayer(playerId);
    }
}
