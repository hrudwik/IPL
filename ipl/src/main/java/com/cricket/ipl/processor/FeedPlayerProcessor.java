package com.cricket.ipl.processor;

import com.cricket.ipl.dao.MatchScheduleDao;
import com.cricket.ipl.dao.PlayerDao;
import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.domain.Player;
import com.cricket.ipl.reader.FeedMatchScheduleReader;
import com.cricket.ipl.reader.FeedPlayerReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public void run() {

        try {
            Player playerInDb = playerDao.selectPlayerById(1);

            if(playerInDb == null) {
                List<Player> players = feedPlayerReader.read();

                for (Player player : players) {
                    writePlayer(player);
                }
            }

            MatchSchedule matchScheduleInDb = matchScheduleDao.selectMatchById(1);
            if(matchScheduleInDb == null) {
                List<MatchSchedule> matchScheduleList = feedMatchScheduleReader.read();

                for (MatchSchedule matchSchedule : matchScheduleList) {
                    writeMatchSchedule(matchSchedule);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
