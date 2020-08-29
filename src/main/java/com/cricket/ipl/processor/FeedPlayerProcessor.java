package com.cricket.ipl.processor;

import com.cricket.ipl.dao.MatchScheduleDao;
import com.cricket.ipl.dao.PlayerDao;
import com.cricket.ipl.dao.UserPredictionDao;
import com.cricket.ipl.dao.UserScorecardDao;
import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.domain.Player;
import com.cricket.ipl.domain.UserPrediction;
import com.cricket.ipl.reader.FeedMatchScheduleReader;
import com.cricket.ipl.reader.FeedPlayerReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("feedPlayerProcessor")
public class FeedPlayerProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedPlayerReader.class);

    @Autowired
    private FeedPlayerReader feedPlayerReader;

    @Autowired
    private FeedMatchScheduleReader feedMatchScheduleReader;

    @Autowired
    @Qualifier("playerDaoImpl")
    private PlayerDao playerDao;

    @Autowired
    @Qualifier("matchScheduleDaoImpl")
    private MatchScheduleDao matchScheduleDao;

    @Autowired
    @Qualifier("userPredictionDaoImpl")
    private UserPredictionDao userPredictionDao;

    @Autowired
    @Qualifier("userScorecardDaoImpl")
    private UserScorecardDao userScorecardDao;

    public void run() {

        try {
            updatePlayersInDb();
            updateMatchScheduleInDb();
            evaluateUserPoints();
            updateOverallUserScoreboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateWinningsForPaidUsers(List<MatchSchedule> matchScheduleList) {

        for (MatchSchedule match : matchScheduleList) {
            if(match.getWinner() != null && !match.getWinner().equals("")) {
                Integer matchId = match.getMatchId();
                List<UserPrediction> userPredictions = userPredictionDao.getPaidUsersPredictionsByMatchId(matchId);

                UserPrediction firstWinner = null;
                UserPrediction secondWinner = null;
                UserPrediction thirdWinner = null;
                if(userPredictions.size() == 0){
                    return;
                } else if(userPredictions.size() == 1){
                    firstWinner = userPredictions.get(0);
                    int firstWinnings = userPredictions.get(0).getBet();
                    firstWinner.setWinnings(firstWinnings);
                } else if(userPredictions.size() == 2) {
                    firstWinner = userPredictions.get(0);
                    secondWinner = userPredictions.get(1);
                    int pot = userPredictions.stream().mapToInt(UserPrediction::getBet).sum();
                    int firstWinnings = pot/2;
                    int secondWinnings = pot/4;
                    if(firstWinner.getPoints().equals(secondWinner.getPoints())) {
                        firstWinnings = (firstWinnings + secondWinnings)/2;
                        secondWinnings = firstWinnings;
                    }
                    firstWinner.setWinnings(firstWinnings);
                    secondWinner.setWinnings(secondWinnings);

                } else {
                    firstWinner = userPredictions.get(0);
                    secondWinner = userPredictions.get(1);
                    thirdWinner = userPredictions.get(2);

                    int pot = userPredictions.stream().mapToInt(UserPrediction::getBet).sum();
                    int firstWinnings = pot/2;
                    int secondWinnings = pot/4;
                    int thirdWinnings = pot/5;

                    if( (firstWinner.getPoints().equals(secondWinner.getPoints())) &&
                            (firstWinner.getPoints().equals(thirdWinner.getPoints())) ) {
                        firstWinnings = (firstWinnings + secondWinnings + thirdWinnings)/3;
                        secondWinnings = firstWinnings;
                        thirdWinnings = firstWinnings;
                    }else if(firstWinner.getPoints().equals(secondWinner.getPoints())) {
                        firstWinnings = (firstWinnings + secondWinnings)/2;
                        secondWinnings = firstWinnings;
                    } else if(secondWinner.getPoints().equals(thirdWinner.getPoints())) {
                        secondWinnings = (secondWinnings + thirdWinnings)/2;
                        thirdWinnings = secondWinnings;
                    }

                    firstWinner.setWinnings(firstWinnings);
                    secondWinner.setWinnings(secondWinnings);
                    thirdWinner.setWinnings(thirdWinnings);
                }

                if(firstWinner != null){
                    userPredictionDao.updateWinnings(firstWinner);
                }
                if(secondWinner != null){
                    userPredictionDao.updateWinnings(secondWinner);
                }
                if(thirdWinner != null){
                    userPredictionDao.updateWinnings(thirdWinner);
                }
            }
        }
    }

    private void updateOverallUserScoreboard() {
        List<Pair<String, Integer>> userPredictions = userPredictionDao.getAllUserSpecificPredictions();

        for (Pair<String, Integer> prediction : userPredictions) {
            String emailId = prediction.getFirst();
            Integer points = prediction.getSecond().intValue();
            userScorecardDao.updateScorecard(emailId, points);
        }
    }

    private void evaluateUserPoints() {

        List<MatchSchedule> matchScheduleList = matchScheduleDao.getMatchSchedule();

        for (MatchSchedule match : matchScheduleList) {
            if(match.getWinner() != null && !match.getWinner().equals("")) {
                Integer matchId = match.getMatchId();
                String winner = match.getWinner();
                String bestBatsmen = match.getBestBatsmen();
                String bestBowler = match.getBestBowler();
                String manOfTheMatch = match.getManOfTheMatch();
                List<UserPrediction> userPredictions = userPredictionDao.getUsersPredictionsByMatchId(matchId);
                for (UserPrediction userPrediction : userPredictions) {
                    // free 5 points for prediction
                    int points = 5;
                    if(userPrediction.getBestBatsmen().equals(bestBatsmen)) {
                        points += 20;
                    }
                    if(userPrediction.getBestBowler().equals(bestBowler)) {
                        points += 20;
                    }
                    if(userPrediction.getManOfTheMatch().equals(manOfTheMatch)) {
                        points += 25;
                    }
                    if(userPrediction.getWinner().equals(winner)) {
                        points += 10;
                    }
                    userPrediction.setPoints(points);
                    userPredictionDao.updatePoints(userPrediction);
                }
            }
        }

        //Finally update user winnings
        updateWinningsForPaidUsers(matchScheduleList);
    }

    private void updatePlayersInDb() {

        Player playerInDb = playerDao.selectPlayerById(1);
        if(playerInDb == null) {
            List<Player> players = feedPlayerReader.read();

            for (Player player : players) {
                writePlayer(player);
            }
        }
    }

    private void updateMatchScheduleInDb() {
        MatchSchedule matchScheduleInDb = matchScheduleDao.selectMatchById(1);
        if(matchScheduleInDb == null) {
            List<MatchSchedule> matchScheduleList = feedMatchScheduleReader.read();

            for (MatchSchedule matchSchedule : matchScheduleList) {
                writeMatchSchedule(matchSchedule);
            }
        }
    }

    public void writeMatchSchedule(MatchSchedule matchSchedule) {

        try {
            matchScheduleDao.insertMatchSchedule(matchSchedule);
        } catch (Throwable e){
            LOGGER.info("MatchSchedule {} didn't make it to DB due to exception {}", matchSchedule, e.getMessage());
            deleteMatchScheduleFromDB(matchSchedule);
            LOGGER.info("Player deleted from DB");
        }
    }

    public void deleteMatchScheduleFromDB(MatchSchedule matchSchedule) {
        matchScheduleDao.deleteMatchSchedule(matchSchedule.getMatchId());
    }

    public void writePlayer(Player player) {

        try {
            playerDao.insertPlayer(player);
        } catch (Throwable e){
            LOGGER.info("Player {} didn't make it to DB due to exception {}", player, e.getMessage());
            deletePlayerToDB(player);
            LOGGER.info("Player deleted from DB");
        }
    }

    public void deletePlayerToDB(Player player) {
        playerDao.deletePlayer(player.getPlayerId());
    }
}
