package com.cricket.ipl.dao;

import com.cricket.ipl.domain.MatchSchedule;

public interface MatchScheduleDao {

    MatchSchedule selectMatchById(Integer matchId);
}
