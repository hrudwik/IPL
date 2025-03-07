package com.cricket.ipl.dao;

import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.UserScorecard;

import java.util.List;

public interface UserScorecardDao {

    boolean insert(UserScorecard userScorecard);

    boolean updateScorecard(String emailId, Integer points);

    boolean updateMoney(String emailId, Integer money);

    UserScorecard getUserScorecard(String emailId);

    List<UserScorecard> getOverallScorecard();
}
