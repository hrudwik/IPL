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
        UserPrediction userPredictionTemp = getUserPredictionByMatchIdAndEmailId(userPrediction.getEmailId(), userPrediction.getMatchId());
        if(userPredictionTemp != null){
            userPrediction.setBet(userPredictionTemp.getBet());
        }

        if(updateMoney(userScorecard, userPrediction, userPredictionTemp)) {
            if(userPredictionTemp == null) {
                insert(userPrediction);
            } else {
                if(userPredictionTemp.getBet()>0){
                    userPrediction.setBet(userPredictionTemp.getBet());
                }
                updatePrediction(userPrediction);
            }
        } else {
            if(leftMoney<userPrediction.getBet()){
                throw new Exception("Prediction update failed for "+userPrediction.getEmailId()+" details :"+ userPrediction);
            }
            if(userPredictionTemp == null) {
                insert(userPrediction);
            } else {
                updatePrediction(userPrediction);
            }
        }

        return  true;
    }

    private boolean updateMoney(UserScorecard userScorecard, UserPrediction userPrediction, UserPrediction userPredictionTemp) {

        boolean isUpdated = false;
        if(userPredictionTemp == null) {
            if(userPrediction.getBet()>0){
                if(userScorecard.getMoney()>=userPrediction.getBet()) {
                    Integer resultantMoney = userScorecard.getMoney() - userPrediction.getBet();
                    isUpdated = userScorecardDao.updateMoney(userPrediction.getEmailId(), resultantMoney);
                }
            }
        } else {
            if(userPrediction.getBet()>0){
                if(userPredictionTemp.getBet() == null || userPredictionTemp.getBet() == 0) {
                    if(userScorecard.getMoney() != null && userScorecard.getMoney()>=userPrediction.getBet()) {
                        Integer resultantMoney = userScorecard.getMoney() - userPrediction.getBet();
                        isUpdated = userScorecardDao.updateMoney(userPrediction.getEmailId(), resultantMoney);
                    }
                }
            }
        }
        return isUpdated;
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
