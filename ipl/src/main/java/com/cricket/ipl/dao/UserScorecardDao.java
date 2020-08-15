package com.cricket.ipl.dao;

import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.UserScorecard;

import java.util.List;

public interface UserScorecardDao {

    boolean insert(UserScorecard userScorecard);

    boolean updateScorecard(String emailId, Integer points);

    List<UserScorecard> getOverallScorecard();
}
