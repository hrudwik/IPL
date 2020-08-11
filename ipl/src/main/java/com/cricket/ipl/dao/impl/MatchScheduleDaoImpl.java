package com.cricket.ipl.dao.impl;

import com.cricket.ipl.dao.MatchScheduleDao;
import com.cricket.ipl.dao.mybatis.mapper.MatchScheduleMapper;
import com.cricket.ipl.dao.mybatis.mapper.PlayerMapper;
import com.cricket.ipl.domain.MatchSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("matchScheduleDaoImpl")
public class MatchScheduleDaoImpl implements MatchScheduleDao {

    @Autowired
    private MatchScheduleMapper matchScheduleMapper;

    @Override
    public MatchSchedule selectMatchById(Integer matchId) {
        return matchScheduleMapper.selectMatchById(matchId);
    }
}
