package com.cricket.ipl.dao.mybatis.mapper;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.dao.VerificationTokenDao;
import com.cricket.ipl.domain.User;
import com.cricket.ipl.domain.VerificationToken;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VerificationTokenMapper extends VerificationTokenDao {

    @Insert("INSERT INTO verificationtoken (token, emailId, expiryDate) VALUES ( #{token}, #{emailId}, #{expiryDate} )")
    boolean insert(VerificationToken verificationToken);

    @Select("SELECT id, token, emailId, expiryDate FROM verificationtoken WHERE token = #{token}")
    VerificationToken getByToken(String token);

    @Select("SELECT id, token, emailId, expiryDate FROM verificationtoken WHERE emailId = #{emailId}")
    VerificationToken getByEmailId(String emailId);
}
