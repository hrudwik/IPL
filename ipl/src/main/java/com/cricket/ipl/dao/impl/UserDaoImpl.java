package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.dao.mybatis.mapper.UserMapper;
import com.cricket.ipl.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public boolean updatePassword(String id, String password) {
        return userMapper.updatePassword(id, password);
    }

    @Override
    public boolean delete(String id) {
        return userMapper.delete(id);
    }

    @Override
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByEmailId(String emailId) { return userMapper.getUserByEmailId(emailId); }

    @Override
    public User getUserByEmailIdAndPassword(String emailId, String password) {
        return userMapper.getUserByEmailIdAndPassword(emailId, password);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }
}
