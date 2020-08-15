package com.cricket.ipl.dao.mybatis.mapper;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.UserPrediction;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserPredictionMapper extends UserDao {

    @Insert("INSERT INTO userprediction (emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points) VALUES " +
            "( #{emailId}, #{matchId}, #{bestBatsmen}, #{bestBowler}, #{manOfTheMatch}, #{winner}, #{points} )")
    boolean insert(UserPrediction userPrediction);

    @Insert("UPDATE userprediction set bestBatsmen=#{bestBatsmen}, bestBowler=#{bestBowler}, manOfTheMatch=#{manOfTheMatch}, " +
            "winner=#{winner} WHERE emailId = #{emailId} AND matchId = #{matchId}")
    boolean updatePrediction(UserPrediction userPrediction);

    @Insert("UPDATE userprediction set points=#{points} WHERE emailId = #{emailId} AND matchId = #{matchId}")
    boolean updatePoints(UserPrediction userPrediction);

    @Select("SELECT emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points FROM userprediction " +
            "WHERE emailId = #{emailId} AND matchId = #{matchId}")
    UserPrediction getUserPredictionByMatchIdAndEmailId(String emailId, Integer matchId);

    @Select("SELECT emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points FROM userprediction " +
            "WHERE matchId = #{matchId}")
    List<UserPrediction> getUserPredictionsByMatchId(Integer matchId);

    @Select("SELECT emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points FROM userprediction")
    List<UserPrediction> getAllUserPredictions();

    @Select("SELECT emailId, CAST(SUM(points) AS INT) FROM userprediction GROUP BY emailId")
    List<Pair<String, Integer>> getAllUserSpecificPredictions();
}
