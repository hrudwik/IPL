package com.cricket.ipl.dao;

import com.cricket.ipl.domain.UserPrediction;
import org.springframework.data.util.Pair;

import java.util.List;

public interface UserPredictionDao {

    boolean upsert(UserPrediction userPrediction) throws Exception;

    boolean insert(UserPrediction userPrediction);

    void updatePrediction(UserPrediction userPrediction);

    void updatePoints(UserPrediction userPrediction);

    void updateWinnings(UserPrediction userPrediction);

    UserPrediction getUserPredictionByMatchIdAndEmailId(String emailId, Integer matchId);

    List<UserPrediction> getUsersPredictionsByMatchId(Integer matchId);

    List<UserPrediction> getPaidUsersPredictionsByMatchId(Integer matchId);

    List<UserPrediction> getAllUserPredictions();

    List<Pair<String, Integer>> getAllUserSpecificPredictions();
}
