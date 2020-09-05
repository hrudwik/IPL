package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.UserPredictionDao;
import com.cricket.ipl.dao.UserScorecardDao;
import com.cricket.ipl.dao.mybatis.mapper.UserPredictionMapper;
import com.cricket.ipl.domain.UserPrediction;
import com.cricket.ipl.domain.UserScorecard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userPredictionDaoImpl")
public class UserPredictionDaoImpl implements UserPredictionDao {

    @Autowired
    private UserPredictionMapper userPredictionMapper;

    @Autowired
    @Qualifier("userScorecardDaoImpl")
    private UserScorecardDao userScorecardDao;

    @Override
    public boolean upsert(UserPrediction userPrediction) throws Exception {
        UserScorecard userScorecard = userScorecardDao.getUserScorecard(userPrediction.getEmailId());
        Integer leftMoney = userScorecard.getMoney();
        Integer betMoney = userPrediction.getBet();

        if(betMoney>leftMoney) {
            throw new Exception("Prediction update failed for "+userPrediction.getEmailId()+". Low balance details leftMoney: "
                    + userPrediction+ " betMoney: "+betMoney);
        }

        UserPrediction userPredictionTemp = getUserPredictionByMatchIdAndEmailId(userPrediction.getEmailId(), userPrediction.getMatchId());
        if(userPredictionTemp == null) {
            if(userPrediction.getBet()>0){
                updateMoney(userScorecard, userPrediction, userPredictionTemp);
            }
            insert(userPrediction);
        } else {
            Integer prevBetMoney = userPredictionTemp.getBet();
            if(betMoney<=prevBetMoney) {
                userPrediction.setBet(userPredictionTemp.getBet());
            } else {
                updateMoney(userScorecard, userPrediction, userPredictionTemp);
            }
            updatePrediction(userPrediction);
        }

        return  true;
    }

    private void updateMoney(UserScorecard userScorecard, UserPrediction userPrediction, UserPrediction userPredictionTemp) {

        Integer resultantMoney = userScorecard.getMoney() - userPrediction.getBet();
        userScorecardDao.updateMoney(userPrediction.getEmailId(), resultantMoney);
    }

    @Override
    public boolean insert(UserPrediction userPrediction) {
        if(userPrediction.getPoints() == null) {
            userPrediction.setPoints(0);
        }
        return userPredictionMapper.insert(userPrediction);
    }

    @Override
    public void updatePrediction(UserPrediction userPrediction) {
        if(userPrediction.getPoints() == null) {
            userPrediction.setPoints(0);
        }
        userPredictionMapper.updatePrediction(userPrediction);
    }

    @Override
    public void updatePoints(UserPrediction userPrediction) {
        userPredictionMapper.updatePoints(userPrediction);
    }

    @Override
    public void updateWinnings(UserPrediction userPrediction) {
        userPredictionMapper.updateWinnings(userPrediction);
    }

    @Override
    public UserPrediction getUserPredictionByMatchIdAndEmailId(String emailId, Integer matchId) {
        return userPredictionMapper.getUserPredictionByMatchIdAndEmailId(emailId, matchId);
    }

    @Override
    public List<UserPrediction> getUsersPredictionsByMatchId(Integer matchId) {
        return userPredictionMapper.getUsersPredictionsByMatchId(matchId);
    }

    @Override
    public List<UserPrediction> getPaidUsersPredictionsByMatchId(Integer matchId) {
        return userPredictionMapper.getPaidUsersPredictionsByMatchId(matchId);
    }

    @Override
    public List<UserPrediction> getAllUserPredictions() {
        return userPredictionMapper.getAllUserPredictions();
    }

    @Override
    public List<Pair<String, Integer>> getAllUserSpecificPredictions() {
        return userPredictionMapper.getAllUserSpecificPredictions();
    }
}
