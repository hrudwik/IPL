package com.cricket.ipl.dao;

import com.cricket.ipl.domain.User;

import java.util.List;

public interface UserDao {

    boolean insert(User user);

    boolean updatePassword(String id, String password);

    boolean delete(String id);

    User getUserById(String id);

    User getUserByEmailId(String emailId);

    User getUserByEmailIdAndPassword(String emailId, String password);

    List<User> getAllUsers();
}
