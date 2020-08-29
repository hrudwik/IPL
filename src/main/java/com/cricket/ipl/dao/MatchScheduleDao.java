package com.cricket.ipl.dao;

import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.domain.Player;

import java.util.List;

public interface MatchScheduleDao {

    void insertMatchSchedule(MatchSchedule matchSchedule);

    List<MatchSchedule> selectNextThreeMatchs();

    List<MatchSchedule> getMatchSchedule();

    MatchSchedule selectMatchById(Integer matchId);

    void deleteMatchSchedule(Integer matchId);
}
