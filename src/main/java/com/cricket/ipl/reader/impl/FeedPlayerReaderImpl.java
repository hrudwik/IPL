package com.cricket.ipl.reader.impl;

import com.cricket.ipl.domain.Player;
import com.cricket.ipl.reader.FeedPlayerReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service("feedPlayerReader")
public class FeedPlayerReaderImpl implements FeedPlayerReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedPlayerReader.class);
    @Value("${players.filename}")
    private String fileName;

    @Override
    public List<Player> read() {
        try (Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(fileName).toURI()))) {
            List<Player> stockPrices = new CsvToBeanBuilder<Player>(reader)
                    .withType(Player.class)
                    .build().parse();
            LOGGER.info("FeedPlayerReader read {} players from file : {}", stockPrices.size(), fileName);
            return stockPrices;
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to players.csv. Due to Exception :" + e.getMessage());
        }
    }
}
