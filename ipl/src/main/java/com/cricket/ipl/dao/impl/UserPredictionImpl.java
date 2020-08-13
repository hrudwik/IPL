package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.dao.UserPredictionDao;
import com.cricket.ipl.dao.mybatis.mapper.UserMapper;
import com.cricket.ipl.dao.mybatis.mapper.UserPredictionMapper;
import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.UserPrediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userPredictionDaoImpl")
public class UserPredictionImpl implements UserPredictionDao {

    @Autowired
    private UserPredictionMapper userPredictionMapper;


    @Override
    public boolean upsert(UserPrediction userPrediction) throws Exception {
        UserPrediction userPredictionTemp = getMatchPrediction(userPrediction.getEmailId(), userPrediction.getMatchId());

        try {
            if(userPredictionTemp == null) {
                insert(userPrediction);
            } else {
                updatePrediction(userPrediction);
            }
        } catch (Exception ex) {
            throw new Exception("Upsert failed");
        }

        return  true;
    }

    @Override
    public boolean insert(UserPrediction userPrediction) {
        return userPredictionMapper.insert(userPrediction);
    }

    @Override
    public boolean updatePrediction(UserPrediction userPrediction) {
        return userPredictionMapper.updatePrediction(userPrediction);
    }

    @Override
    public UserPrediction getMatchPrediction(String emailId, Integer matchId) {
        return userPredictionMapper.getMatchPrediction(emailId, matchId);
    }
}
