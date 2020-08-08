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

    @Insert("INSERT INTO \"user\"(id, emailId, userName, password) VALUES ( #{id}, #{emailId}, #{userName}, #{password} )")
    boolean insert(User user);

    @Insert("UPDATE \"user\" set password=#{password} WHERE id = #{id})")
    boolean updatePassword(String id, String password);

    @Delete("DELETE FROM \"user\" WHERE id = #{id}")
    boolean delete(String id);

    @Select("SELECT id, userName, emailId, password FROM \"user\" WHERE id = #{id}")
    User getUserById(String id);

    @Select("SELECT id, userName, emailId, password FROM \"user\" WHERE emailId = #{emailId}")
    User getUserByEmailId(String emailId);

    @Select("SELECT id, userName, emailId, password FROM \"user\" WHERE emailId = #{emailId} and password = #{password}")
    User getUserByEmailIdAndPassword(String emailId, String password);

    @Select("SELECT id, userName, emailId, password FROM \"user\"")
    List<User> getAllUsers();
}
