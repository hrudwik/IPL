package com.cricket.ipl.controller;

import com.cricket.ipl.dao.*;
import com.cricket.ipl.domain.*;
import com.cricket.ipl.events.OnRegistrationCompleteEvent;
import com.cricket.ipl.util.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

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
    // @CrossOrigin(origins = {NetworkConstants.URL1, NetworkConstants.URL2, NetworkConstants.URL3, NetworkConstants.URL4})
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

        user.setEnabled(false);
        userDao.insert(user);

        confirmRegistration(user);
        // String appUrl = request.getContextPath();
        // eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));

        return user;
    }

    private void confirmRegistration(User user) {
        String token = UUID.randomUUID().toString();
        userDao.createVerificationToken(user, token);

        String recipientAddress = user.getEmailId();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = "http://ipl-prediction-t20.herokuapp.com" + "/regitrationConfirm.html?token=" + token;
        // String message = messages.getMessage("message.regSucc", null, Locale.ENGLISH);
        String message = "Hi " + user.getUserName() + " please click on below url to confirm registration";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + confirmationUrl);
        try {
            mailSender.send(email);
        } catch(MailException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/regitrationConfirm")
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userDao.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        String emailId = verificationToken.getEmailId();
        User user = userDao.getUserByEmailId(emailId);
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        userDao.updateEnabled(user);
        return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    }

    @PostMapping("/login")
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
    public Boolean updateUserPrediction(@RequestBody UserPrediction userPrediction) throws Exception {

        try {
            LOGGER.info("updating user prediction {}, {}", userPrediction.getEmailId(), userPrediction);
            return userPredictionDao.upsert(userPrediction);
        } catch (Exception ex) {
            throw new Exception("Prediction update failed for "+userPrediction.getEmailId()+" details :"+ userPrediction);
        }
    }

    @PostMapping("/getUserPrediction")
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
    public List<UserPrediction> getLeaderboardForMatch(@RequestBody MatchDetails matchDetails) throws Exception {

        try {
            return userPredictionDao.getUsersPredictionsByMatchId(matchDetails.getMatchId());
        } catch (Exception ex) {
            throw new Exception("failed to fetch overall scorecard");
        }
    }

    @PostMapping("/getPaidLeaderboardForMatch")
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
