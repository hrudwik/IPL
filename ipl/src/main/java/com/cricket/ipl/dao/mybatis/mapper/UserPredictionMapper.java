package com.cricket.ipl.dao.mybatis.mapper;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.UserPrediction;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserPredictionMapper extends UserDao {

    @Insert("INSERT INTO userprediction (emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner) VALUES " +
            "( #{emailId}, #{matchId}, #{bestBatsmen}" +
            ", #{bestBowler}, #{manOfTheMatch}, #{winner} )")
    boolean insert(UserPrediction userPrediction);

    @Insert("UPDATE userprediction set bestBatsmen=#{bestBatsmen}, bestBowler=#{bestBowler}, manOfTheMatch=#{manOfTheMatch}, winner=#{winner}" +
            "WHERE emailId = #{emailId} AND matchId = #{matchId}")
    boolean updatePrediction(UserPrediction userPrediction);

    @Select("SELECT emailId, matchId, bestBatsmen, bestBowler, manOfTheMatch, winner FROM userprediction " +
            "WHERE emailId = #{emailId} AND matchId = #{matchId}")
    UserPrediction getMatchPrediction(String emailId, Integer matchId);
}
