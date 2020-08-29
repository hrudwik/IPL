package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.MatchScheduleDao;
import com.cricket.ipl.dao.mybatis.mapper.MatchScheduleMapper;
import com.cricket.ipl.dao.mybatis.mapper.PlayerMapper;
import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("matchScheduleDaoImpl")
public class MatchScheduleDaoImpl implements MatchScheduleDao {

    @Autowired
    private MatchScheduleMapper matchScheduleMapper;

    @Override
    public void insertMatchSchedule(MatchSchedule matchSchedule) {
        matchScheduleMapper.insertMatchSchedule(matchSchedule);
    }

    @Override
    public List<MatchSchedule> selectNextThreeMatchs() {
        return matchScheduleMapper.selectNextThreeMatchs();
    }

    @Override
    public List<MatchSchedule> getMatchSchedule() {
        return matchScheduleMapper.getMatchSchedule();
    }

    @Override
    public MatchSchedule selectMatchById(Integer matchId) {
        return matchScheduleMapper.selectMatchById(matchId);
    }

    @Override
    public void deleteMatchSchedule(Integer matchId) {
        matchScheduleMapper.deleteMatchSchedule(matchId);
    }
}
