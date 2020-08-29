package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.dao.UserScorecardDao;
import com.cricket.ipl.dao.mybatis.mapper.UserMapper;
import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.UserScorecard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("userScorecardDaoImpl")
    private UserScorecardDao userScorecardDao;

    @Override
    public boolean insert(User user) {
        boolean isInserted = userMapper.insert(user);

        // Update into UserScorecard table
        user = getUserByEmailId(user.getEmailId());
        insertUserToUserScorecard(user);

        return isInserted;
    }

    @Override
    public boolean updatePassword(Integer id, String password) {
        return userMapper.updatePassword(id, password);
    }

    @Override
    public boolean delete(Integer id) {
        return userMapper.delete(id);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByEmailId(String emailId) { return userMapper.getUserByEmailId(emailId); }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userMapper.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public User getUserByEmailIdAndPassword(String emailId, String password) {
        return userMapper.getUserByEmailIdAndPassword(emailId, password);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    private void insertUserToUserScorecard(User user) {

        UserScorecard userScorecard = new UserScorecard(user.getId(), user.getEmailId(), user.getUserName(), 0, 0);
        userScorecardDao.insert(userScorecard);
    }
}
