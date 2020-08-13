package com.cricket.ipl.dao;

import com.cricket.ipl.domain.UserPrediction;

public interface UserPredictionDao {

    boolean upsert(UserPrediction userPrediction) throws Exception;

    boolean insert(UserPrediction userPrediction);

    boolean updatePrediction(UserPrediction userPrediction);

    UserPrediction getMatchPrediction(String emailId, Integer matchId);
}
