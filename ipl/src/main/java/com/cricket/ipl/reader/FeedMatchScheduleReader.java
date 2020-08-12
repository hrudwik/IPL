package com.cricket.ipl.reader;

import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.domain.Player;

import java.util.List;

public interface FeedMatchScheduleReader {
    List<MatchSchedule> read();
}
