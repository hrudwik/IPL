package com.cricket.ipl.dao;

import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.VerificationToken;

import java.util.List;

public interface VerificationTokenDao {

    boolean insert(VerificationToken verificationToken);

    VerificationToken getByToken(String token);

    VerificationToken getByEmailId(String emailId);
}
