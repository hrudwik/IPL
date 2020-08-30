package com.cricket.ipl.dao.mybatis.mapper;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends UserDao {

    @Insert("INSERT INTO \"user\"(emailId, userName, phoneNumber, password, enabled, token) VALUES ( #{emailId}, #{userName}, #{phoneNumber}" +
            ", #{password}, #{enabled}, #{token} )")
    boolean insert(User user);

    @Insert("UPDATE \"user\" set password=#{password} WHERE emailId = #{emailId}")
    boolean updatePassword(String emailId, String password);

    @Insert("UPDATE \"user\" set enabled=#{enabled} WHERE emailId = #{emailId}")
    boolean updateEnabled(User user);

    @Delete("DELETE FROM \"user\" WHERE id = #{id}")
    boolean delete(Integer id);

    @Select("SELECT id, emailId, userName, phoneNumber, password, enabled, token FROM \"user\" WHERE id = #{id}")
    User getUserById(Integer id);

    @Select("SELECT id, emailId, userName, phoneNumber, password, enabled, token FROM \"user\" WHERE emailId = #{emailId}")
    User getUserByEmailId(String emailId);

    @Select("SELECT id, emailId, userName, phoneNumber, password, enabled, token FROM \"user\" WHERE userName = #{userName}")
    User getUserByUserName(String userName);

    @Select("SELECT id, emailId, userName, phoneNumber, password, enabled, token FROM \"user\" WHERE phoneNumber = #{phoneNumber}")
    User getUserByPhoneNumber(String phoneNumber);

    @Select("SELECT id, emailId, userName, phoneNumber, password, enabled, token FROM \"user\" WHERE emailId = #{emailId} and password = #{password}")
    User getUserByEmailIdAndPassword(String emailId, String password);

    @Select("SELECT id, emailId, userName, phoneNumber, password, enabled, token FROM \"user\"")
    List<User> getAllUsers();
}
