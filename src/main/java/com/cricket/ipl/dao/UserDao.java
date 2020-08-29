package com.cricket.ipl.dao;

import com.cricket.ipl.domain.User;

import java.util.List;

public interface UserDao {

    boolean insert(User user);

    boolean updatePassword(Integer id, String password);

    boolean delete(Integer id);

    User getUserById(Integer id);

    User getUserByEmailId(String emailId);

    User getUserByUserName(String userName);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserByEmailIdAndPassword(String emailId, String password);

    List<User> getAllUsers();
}
