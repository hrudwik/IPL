package com.cricket.ipl.dao.mybatis.mapper;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.dao.UserPredictionDao;
import com.cricket.ipl.domain.UserPrediction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.util.Pair;

import java.util.List;

@Mapper
public interface UserPredictionMapper extends UserPredictionDao {

    @Insert("INSERT INTO userprediction (emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points, bet, winnings) VALUES " +
            "( #{emailId}, #{matchId}, #{bestBatsmen}, #{bestBowler}, #{manOfTheMatch}, #{winner}, #{points}, #{bet}, #{winnings} )")
    boolean insert(UserPrediction userPrediction);

    @Insert("UPDATE userprediction set bestBatsmen=#{bestBatsmen}, bestBowler=#{bestBowler}, manOfTheMatch=#{manOfTheMatch}, " +
            "winner=#{winner}, bet=#{bet}, winnings=#{winnings} WHERE emailId = #{emailId} AND matchId = #{matchId}")
    void updatePrediction(UserPrediction userPrediction);

    @Insert("UPDATE userprediction set points=#{points} WHERE emailId = #{emailId} AND matchId = #{matchId}")
    void updatePoints(UserPrediction userPrediction);

    @Insert("UPDATE userprediction set winnings=#{winnings} WHERE emailId = #{emailId} AND matchId = #{matchId}")
    void updateWinnings(UserPrediction userPrediction);

    @Select("SELECT emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points, bet, winnings FROM userprediction " +
            "WHERE emailId = #{emailId} AND matchId = #{matchId}")
    UserPrediction getUserPredictionByMatchIdAndEmailId(String emailId, Integer matchId);

    @Select("SELECT emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points, bet, winnings FROM userprediction " +
            "WHERE matchId = #{matchId} ORDER BY points desc")
    List<UserPrediction> getUsersPredictionsByMatchId(Integer matchId);

    @Select("SELECT emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points, bet, winnings FROM userprediction " +
            "WHERE matchId = #{matchId} AND bet IS NOT NULL AND bet<>0 ORDER BY points desc")
    List<UserPrediction> getPaidUsersPredictionsByMatchId(Integer matchId);

    @Select("SELECT emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner, points, bet, winnings FROM userprediction")
    List<UserPrediction> getAllUserPredictions();

    @Select("SELECT emailId, CAST(SUM(points) AS INT) FROM userprediction GROUP BY emailId")
    List<Pair<String, Integer>> getAllUserSpecificPredictions();
}
