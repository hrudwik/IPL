package com.cricket.ipl.controller;

import com.cricket.ipl.dao.*;
import com.cricket.ipl.domain.*;
import com.cricket.ipl.util.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    @Qualifier("userScorecardDaoImpl")
    private UserScorecardDao userScorecardDao;

    @Autowired
    @Qualifier("matchScheduleDaoImpl")
    private MatchScheduleDao matchScheduleDao;

    @Autowired
    @Qualifier("playerDaoImpl")
    private PlayerDao playerDao;

    @ResponseStatus(value= HttpStatus.CONFLICT, reason="user with this Email Id already exists")  // 409
    public class UserConflictException extends RuntimeException {
        public UserConflictException(String message) {
            super(message);
        }
    }

    @PostMapping("/registeruser")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
    public User registerUser(@RequestBody User user) throws Exception {
        String tempEmailId = user.getEmailId();
        String tempUserName = user.getUserName();
        String tempPhoneNo = user.getPhoneNumber();
        if(tempEmailId!=null && !"".equals(tempEmailId)) {
            User userObj =  userDao.getUserByEmailId(tempEmailId);
            if(userObj != null) {
                throw new ConstraintViolationException("User with email id \""+tempEmailId+"\" already exists", Collections.emptySet());
            }
        }
        if(tempUserName!=null) {
            User userObj = userDao.getUserByUserName(tempUserName);
            if(userObj != null) {
                throw new ConstraintViolationException("User with user name \""+tempUserName+"\" already exists", Collections.emptySet());
            }
        }
        if(tempPhoneNo!=null) {
            User userObj = userDao.getUserByPhoneNumber(tempPhoneNo);
            if(userObj != null) {
                throw new ConstraintViolationException("User with phone number \""+tempPhoneNo+"\" already exists", Collections.emptySet());
            }
        }
        userDao.insert(user);
        return user;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
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
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
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

    @PostMapping("/getAllMatchDetails")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
    public List<MatchDetails> getAllMatchDetails() throws Exception {
        List<MatchDetails> matchDetailList = new ArrayList<>();
        List<MatchSchedule> matchScheduleList = matchScheduleDao.getMatchSchedule();
        List<MatchSchedule> orderedMatchScheduleList = matchScheduleList.stream().filter(ms->ms.getWinner() != null)
                .sorted(Comparator.comparing(MatchSchedule::getMatchId).reversed())
                .collect(Collectors.toList());

        for (MatchSchedule matchSchedule:orderedMatchScheduleList) {
            MatchDetails matchDetails = new MatchDetails();
            matchDetails.setMatchId(matchSchedule.getMatchId());
            matchDetails.setTeamName1(matchSchedule.getTeamName1());
            matchDetails.setTeamName2(matchSchedule.getTeamName2());
            matchDetails.setScheduleDate(matchSchedule.getScheduleDate());

            matchDetailList.add(matchDetails);
        }

        return matchDetailList;
    }

    @PostMapping("/updateUserPrediction")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
    public Boolean updateUserPrediction(@RequestBody UserPrediction userPrediction) throws Exception {

        try {
            LOGGER.info("updating user prediction {}, {}", userPrediction.getEmailId(), userPrediction);
            return userPredictionDao.upsert(userPrediction);
        } catch (Exception ex) {
            throw new Exception("Prediction update failed for "+userPrediction.getEmailId()+" details :"+ userPrediction);
        }
    }

    @PostMapping("/getUserPrediction")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
    public UserPrediction getUserPrediction(@RequestBody UserPrediction userPrediction) throws Exception {

        try {
            return userPredictionDao.getUserPredictionByMatchIdAndEmailId(userPrediction.getEmailId(), userPrediction.getMatchId());
        } catch (Exception ex) {
            throw new Exception("Prediction update failed for "+userPrediction.getEmailId()+" details :"+ userPrediction);
        }
    }

    @PostMapping("/getOverallLeaderboard")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
    public List<UserScorecard> getOverallLeaderboard() throws Exception {

        try {
            return userScorecardDao.getOverallScorecard();
        } catch (Exception ex) {
            throw new Exception("failed to fetch overall scorecard");
        }
    }

    @PostMapping("/getLeaderboardForMatch")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
    public List<UserPrediction> getLeaderboardForMatch(@RequestBody MatchDetails matchDetails) throws Exception {

        try {
            return userPredictionDao.getUsersPredictionsByMatchId(matchDetails.getMatchId());
        } catch (Exception ex) {
            throw new Exception("failed to fetch overall scorecard");
        }
    }

    @PostMapping("/getPaidLeaderboardForMatch")
    @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
    public List<UserPrediction> getPaidLeaderboardForMatch(@RequestBody MatchDetails matchDetails) throws Exception {

        try {
            return userPredictionDao.getPaidUsersPredictionsByMatchId(matchDetails.getMatchId());
        } catch (Exception ex) {
            throw new Exception("failed to fetch overall scorecard");
        }
    }


    /*@RequestMapping("/users")
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
    }*/
}
