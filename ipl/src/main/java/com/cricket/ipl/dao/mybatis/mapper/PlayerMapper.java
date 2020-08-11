package com.cricket.ipl.dao.mybatis.mapper;

import com.cricket.ipl.dao.PlayerDao;
import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.domain.Player;
import com.cricket.ipl.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlayerMapper extends PlayerDao {

    @Insert("INSERT INTO players(playerId, playerName, teamName) VALUES ( #{playerId}, #{playerName}, #{teamName} )")
    void insertPlayer(Player player);

    @Select("SELECT playerId, playerName, teamName FROM players WHERE playerId = #{playerId}")
    Player selectPlayerById(Integer playerId);

    @Select("SELECT playerId, playerName, teamName FROM players WHERE playerName = #{playerName}")
    Player selectPlayerByName(String playerName);

    @Select("SELECT playerId, playerName, teamName FROM players ")
    List<Player> selectAllPlayers();

    @Select("SELECT playerId, playerName, teamName FROM players WHERE teamName = #{teamName}")
    List<Player> selectAllPlayersByTeamName( String teamName);

    @Select("SELECT playerId, playerName, teamName FROM players WHERE teamName = #{teamName1} OR teamName = #{teamName2}")
    List<Player> selectAllPlayersByTeamNames( String teamName1, String teamName2);

    @Delete("DELETE players FROM  WHERE playerId = #{playerId}")
    void deletePlayer(Integer playerId);
}
