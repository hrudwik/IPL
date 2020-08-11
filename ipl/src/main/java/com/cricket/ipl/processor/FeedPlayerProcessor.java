package com.cricket.ipl.processor;

import com.cricket.ipl.dao.PlayerDao;
import com.cricket.ipl.domain.Player;
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
    @Qualifier("playerDaoImpl")
    private PlayerDao playerDao;

    public void run() {

        try {
            Player playerInDb = playerDao.selectPlayerById(1);

            if(playerInDb == null) {
                List<Player> players = feedPlayerReader.read();

                for (Player player : players) {
                    writeStockPrice(player);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeStockPrice(Player player) {

        try {
            playerDao.insertPlayer(player);
        } catch (Throwable e){
            LOGGER.info("Player {} didn't make it to DB due to exception {}", player, e.getMessage());
            deleteStockPriceToDB(player);
            LOGGER.info("Player deleted from DB");
        }
    }

    public void deleteStockPriceToDB(Player player) {
        playerDao.deletePlayer(player.getPlayerId());
    }
}
