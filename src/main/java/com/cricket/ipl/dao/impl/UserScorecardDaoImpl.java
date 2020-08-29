package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.dao.UserScorecardDao;
import com.cricket.ipl.dao.mybatis.mapper.UserMapper;
import com.cricket.ipl.dao.mybatis.mapper.UserScorecardMapper;
import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.UserScorecard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userScorecardDaoImpl")
public class UserScorecardDaoImpl implements UserScorecardDao {

    @Autowired
    private UserScorecardMapper userScorecardMapper;


    @Override
    public boolean insert(UserScorecard userScorecard) {
        return userScorecardMapper.insert(userScorecard);
    }

    @Override
    public boolean updateScorecard(String emailId, Integer points) {
        return userScorecardMapper.updateScorecard(emailId, points);
    }

    @Override
    public boolean updateMoney(String emailId, Integer money) {
        return userScorecardMapper.updateMoney(emailId, money);
    }

    @Override
    public UserScorecard getUserScorecard(String emailId) {
        return  userScorecardMapper.getUserScorecard(emailId);
    }


    @Override
    public List<UserScorecard> getOverallScorecard() {
        return userScorecardMapper.getOverallScorecard();
    }
}
