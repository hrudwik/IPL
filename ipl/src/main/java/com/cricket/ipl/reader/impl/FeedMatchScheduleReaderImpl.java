package com.cricket.ipl.reader.impl;

import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.reader.FeedMatchScheduleReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service("feedMatchScheduleReader")
public class FeedMatchScheduleReaderImpl implements FeedMatchScheduleReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedMatchScheduleReader.class);
    @Value("${matchschedule.filename}")
    private String fileName;

    @Override
    public List<MatchSchedule> read() {
        try (Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(fileName).toURI()))) {
            List<MatchSchedule> matchScheduleList = new CsvToBeanBuilder<MatchSchedule>(reader)
                    .withType(MatchSchedule.class)
                    .build().parse();
            LOGGER.info("FeedMatchScheduleReader read {} matches from file : {}", matchScheduleList.size(), fileName);
            return matchScheduleList;
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to players.csv. Due to Exception :" + e.getMessage());
        }
    }
}
