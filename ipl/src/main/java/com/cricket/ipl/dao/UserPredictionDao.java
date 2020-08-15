package com.cricket.ipl.dao;

import com.cricket.ipl.domain.UserPrediction;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface UserPredictionDao {

    boolean upsert(UserPrediction userPrediction) throws Exception;

    boolean insert(UserPrediction userPrediction);

    boolean updatePrediction(UserPrediction userPrediction);

    boolean updatePoints(UserPrediction userPrediction);

    UserPrediction getUserPredictionByMatchIdAndEmailId(String emailId, Integer matchId);

    List<UserPrediction> getUserPredictionsByMatchId(Integer matchId);

    List<UserPrediction> getAllUserPredictions();

    List<Pair<String, Integer>> getAllUserSpecificPredictions();
}
