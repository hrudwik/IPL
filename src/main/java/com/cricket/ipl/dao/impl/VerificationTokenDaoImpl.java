package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.VerificationTokenDao;
import com.cricket.ipl.dao.mybatis.mapper.VerificationTokenMapper;
import com.cricket.ipl.domain.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("verificationTokenDaoImpl")
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @Autowired
    private VerificationTokenMapper verificationTokenMapper;

    @Override
    public boolean insert(VerificationToken verificationToken) {
        return verificationTokenMapper.insert(verificationToken);
    }

    @Override
    public VerificationToken getByToken(String token) {
        return verificationTokenMapper.getByToken(token);
    }

    @Override
    public VerificationToken getByEmailId(String emailId) {
        return verificationTokenMapper.getByEmailId(emailId);
    }
}
