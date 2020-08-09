package com.cricket.ipl.controller;

import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.util.NetworkConstants;
import com.cricket.ipl.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;

    @PostMapping("/registeruser")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2})
    public User registerUser(@RequestBody User user) throws Exception {
        String tempEmailId = user.getEmailId();
        if(tempEmailId!=null && !"".equals(tempEmailId)) {
            User userObj =  userDao.getUserByEmailId(tempEmailId);
            if(userObj != null) {
                throw new Exception("user with "+tempEmailId+" already exists");
            }
        }
        userDao.insert(user);
        return user;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2})
    public User loginUser(@RequestBody User user) throws Exception {
        String tempEmailId = user.getEmailId();
        String tempPassword = user.getPassword();
        User userObj = null;
        if(tempEmailId!=null && !"".equals(tempEmailId) && tempPassword!=null && !"".equals(tempPassword)) {
            userObj =  userDao.getUserByEmailIdAndPassword(tempEmailId, tempPassword);
        }
        if(userObj == null) {
            throw new Exception("Bad credentials");
        }
        return userObj;
    }

    @RequestMapping("/users")
    public List<User> getTopicList(){
        return userDao.getAllUsers();
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable Integer id){
        return userDao.getUserById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public void updateUser(@RequestBody User user, @PathVariable Integer id){
        userDao.updatePassword(id, user.getPassword());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userDao.delete(id);
    }
}
