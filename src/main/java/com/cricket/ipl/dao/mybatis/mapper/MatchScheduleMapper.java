package com.cricket.ipl.dao.mybatis.mapper;

import com.cricket.ipl.dao.MatchScheduleDao;
import com.cricket.ipl.dao.PlayerDao;
import com.cricket.ipl.domain.MatchSchedule;
import com.cricket.ipl.domain.Player;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatchScheduleMapper extends MatchScheduleDao {

    @Insert("INSERT INTO matchschedule(matchId, teamName1, teamName2, scheduleDate) VALUES ( #{matchId}, #{teamName1}, #{teamName2}, #{scheduleDate} )")
    void insertMatchSchedule(MatchSchedule matchSchedule);

    @Select("SELECT matchId, teamName1, teamName2, scheduleDate, bestBatsmen, bestBowler, manOfTheMatch, winner" +
            " FROM matchschedule WHERE matchId = #{matchId}")
    public MatchSchedule selectMatchById(Integer matchId);

    @Select("SELECT matchId, teamName1, teamName2, scheduleDate, bestBatsmen, bestBowler, manOfTheMatch, winner" +
            " FROM matchschedule where scheduleDate > now() ORDER BY matchId limit 3")
    public List<MatchSchedule> selectNextThreeMatchs();

    @Select("SELECT matchId, teamName1, teamName2, scheduleDate, bestBatsmen, bestBowler, manOfTheMatch, winner" +
            " FROM matchschedule ORDER BY matchId")
    public List<MatchSchedule> getMatchSchedule();

    @Delete("DELETE matchschedule FROM  WHERE matchId = #{matchId}")
    void deleteMatchSchedule(Integer matchId);
}
