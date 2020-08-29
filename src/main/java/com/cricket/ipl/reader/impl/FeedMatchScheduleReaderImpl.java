package com.cricket.ipl.reader.impl;

import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.reader.FeedMatchScheduleReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
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
        List<MatchSchedule> matchScheduleList = null;
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream inputStream = cl.getResourceAsStream(fileName);
        assert inputStream != null;
        try (Reader reader = new InputStreamReader(inputStream) ) {
            matchScheduleList = new CsvToBeanBuilder<MatchSchedule>(reader)
                    .withType(MatchSchedule.class)
                    .build().parse();
            LOGGER.info("FeedMatchScheduleReader read {} matches from file : {}", matchScheduleList.size(), fileName);
            return matchScheduleList;
        } catch (Exception e) {
            LOGGER.error("Unable to read all mathces from matchscedule.csv. Due to Exception :" + e.getMessage());
            return matchScheduleList;
        } finally {
            return matchScheduleList;
        }
    }
}
