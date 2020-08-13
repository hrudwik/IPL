package com.cricket.ipl.controller;

import com.cricket.ipl.IplApplication;
import com.cricket.ipl.dao.MatchScheduleDao;
import com.cricket.ipl.dao.PlayerDao;
import com.cricket.ipl.dao.UserDao;
import com.cricket.ipl.dao.UserPredictionDao;
import com.cricket.ipl.domain.*;
import com.cricket.ipl.util.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;

    @Autowired
    @Qualifier("userPredictionDaoImpl")
    private UserPredictionDao userPredictionDao;

    @Autowired
    @Qualifier("matchScheduleDaoImpl")
    private MatchScheduleDao matchScheduleDao;

    @Autowired
    @Qualifier("playerDaoImpl")
    private PlayerDao playerDao;

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

    @PostMapping("/nextThreeMatchDetails")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2})
    public List<MatchDetails> nextThreeMatchDetails() throws Exception {
        List<MatchDetails> matchDetailList = new ArrayList<>();
        List<MatchSchedule> matchScheduleList = matchScheduleDao.selectNextThreeMatchs();

        for (MatchSchedule matchSchedule:matchScheduleList) {
            MatchDetails matchDetails = new MatchDetails();
            List<Player> players = playerDao.selectAllPlayersByTeamNames(matchSchedule.getTeamName1(), matchSchedule.getTeamName2());
            List<String> playerNames = players.stream().map(Player::getPlayerName).collect(Collectors.toList());

            matchDetails.setMatchId(matchSchedule.getMatchId());
            matchDetails.setTeamName1(matchSchedule.getTeamName1());
            matchDetails.setTeamName2(matchSchedule.getTeamName2());
            matchDetails.setScheduleDate(matchSchedule.getScheduleDate());
            matchDetails.setPlayers(playerNames);

            matchDetailList.add(matchDetails);
        }

        return matchDetailList;
    }

    @PostMapping("/updateUserPrediction")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2})
    public Boolean updateUserPrediction(@RequestBody UserPrediction userPrediction) throws Exception {

        try {
            return userPredictionDao.upsert(userPrediction);
        } catch (Exception ex) {
            throw new Exception("Prediction update failed for "+userPrediction.getEmailId()+" details :"+ userPrediction);
        }
    }

    @PostMapping("/getUserPrediction")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2})
    public UserPrediction getUserPrediction(@RequestBody UserPrediction userPrediction) throws Exception {

        try {
            return userPredictionDao.getMatchPrediction(userPrediction.getEmailId(), userPrediction.getMatchId());
        } catch (Exception ex) {
            throw new Exception("Prediction update failed for "+userPrediction.getEmailId()+" details :"+ userPrediction);
        }
    }


    // @RequestMapping("/users")
    public List<User> getTopicList(){
        return userDao.getAllUsers();
    }

    // @RequestMapping("/users/{id}")
    public User getUser(@PathVariable Integer id){
        return userDao.getUserById(id);
    }

    // @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public void updateUser(@RequestBody User user, @PathVariable Integer id){
        userDao.updatePassword(id, user.getPassword());
    }

    // @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userDao.delete(id);
    }
}
