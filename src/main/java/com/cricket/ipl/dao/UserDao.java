package com.cricket.ipl.dao;

import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.VerificationToken;

import java.util.List;

public interface UserDao {

    boolean insert(User user);

    boolean updatePassword(String emailId, String password);

    boolean updateEnabled(User user);

    boolean updateToken(User user);

    boolean delete(Integer id);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    User getUserByVerificationToken(String verificationToken);

    User getUserById(Integer id);

    User getUserByEmailId(String emailId);

    User getUserByToken(String token);

    User getUserByUserName(String userName);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserByEmailIdAndPassword(String emailId, String password);

    List<User> getAllUsers();
}
