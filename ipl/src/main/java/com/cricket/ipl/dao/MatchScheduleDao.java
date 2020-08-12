package com.cricket.ipl.dao;

import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.domain.Player;

public interface MatchScheduleDao {

    void insertMatchSchedule(MatchSchedule matchSchedule);

    MatchSchedule selectMatchById(Integer matchId);

    void deleteMatchSchedule(Integer matchId);
}
