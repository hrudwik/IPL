package com.cricket.ipl.dao.mybatis.mapper;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.UserScorecard;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserScorecardMapper extends UserDao {


    @Insert("INSERT INTO userscorecard (userId, emailId, userName, points) VALUES " +
            "( #{userId}, #{emailId}, #{userName}, #{points} )")
    boolean insert(UserScorecard userScorecard);

    @Insert("UPDATE userscorecard set points=#{points} WHERE emailId = #{emailId}")
    boolean updateScorecard(String emailId, Integer points);

    @Select("SELECT userId, emailId, userName, points FROM userscorecard order by points desc")
    List<UserScorecard> getOverallScorecard();
}
