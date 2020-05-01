package com.example.login.repos;

import com.example.login.domain.TimeTable;
import com.example.login.domain.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface TimeRepo extends JpaRepository<TimeTable, Integer> {
    List<TimeTable> findByPersonId(Long id);
    List<TimeTable> findByPersonIdAndEventAndDate(Long id,String event, Date date);
    //for all time
    @Query(value = "SELECT SUM(avg )/COUNT(avg) FROM time_table WHERE user_id=?1 AND time_table.event='Exit' ", nativeQuery = true)Double selectTotalsAvg(Integer id);
    @Query(value = "SELECT max(time_table.max) FROM time_table WHERE user_id=?1 AND time_table.max!=0  ", nativeQuery = true)Integer selectTotalsMax(Integer id);
    @Query(value = "SELECT min(time_table.min) FROM time_table WHERE user_id=?1 AND time_table.min!=0  ", nativeQuery = true)Integer selectTotalsMin(Integer id);
    @Query(value = "SELECT count(time_table.event) FROM time_table WHERE user_id=?1 AND time_table.event='Enter in system'", nativeQuery = true)Integer selectTotalsEnters(Integer id);
    @Query(value = "SELECT sum(time_table.questions) FROM time_table WHERE user_id=?1", nativeQuery = true)Integer amountquestions(Integer id);
    @Query(value = "SELECT sum(time_table.corect) FROM time_table WHERE user_id=?1", nativeQuery = true)Integer amountquestionscorect(Integer id);


    //for last day
    @Query(value = "SELECT SUM(avg )/COUNT(avg) FROM time_table WHERE user_id=?1 AND time_table.event='Exit' AND time_table.date >= date_trunc('Day',Now()) - interval '0 days' ", nativeQuery = true)Double selectdayAvg(Integer id);
    @Query(value = "SELECT max(time_table.max) FROM time_table WHERE user_id=?1 AND time_table.max!=0 AND time_table.date >= date_trunc('Day',Now()) - interval '0 days' ", nativeQuery = true)Integer selectdayMax(Integer id);
    @Query(value = "SELECT min(time_table.min) FROM time_table WHERE user_id=?1 AND time_table.min!=0  AND time_table.date >= date_trunc('Day',Now()) - interval '0 days'", nativeQuery = true)Integer selectdayMin(Integer id);
    @Query(value = "SELECT count(time_table.event) FROM time_table WHERE user_id=?1 AND time_table.event='Enter in system' AND time_table.date >= date_trunc('Day',Now()) - interval '0 days'", nativeQuery = true)Integer selectdayEnters(Integer id);
    @Query(value = "SELECT sum(time_table.questions) FROM time_table WHERE user_id=?1 AND time_table.date >= date_trunc('Day',Now()) - interval '0 days'", nativeQuery = true)Integer amountquestionsday(Integer id);
    @Query(value = "SELECT sum(time_table.corect) FROM time_table WHERE user_id=?1 AND time_table.date >= date_trunc('Day',Now()) - interval '0 days' ", nativeQuery = true)Integer amountquestionscorectday(Integer id);


    //for last month
    @Query(value = "SELECT SUM(avg )/COUNT(avg) FROM time_table WHERE user_id=?1 AND time_table.event='Exit' AND time_table.date >= date_trunc('Day',Now()) - interval '30 days' ", nativeQuery = true)Double selectmonthAvg(Integer id);
    @Query(value = "SELECT max(time_table.max) FROM time_table WHERE user_id=?1 AND time_table.max!=0 AND time_table.date >= date_trunc('Day',Now()) - interval '30 days' ", nativeQuery = true)Integer selectmonthMax(Integer id);
    @Query(value = "SELECT min(time_table.min) FROM time_table WHERE user_id=?1 AND time_table.min!=0  AND time_table.date >= date_trunc('Day',Now()) - interval '30 days'", nativeQuery = true)Integer selectmonthMin(Integer id);
    @Query(value = "SELECT count(time_table.event) FROM time_table WHERE user_id=?1 AND time_table.event='Enter in system' AND time_table.date >= date_trunc('Day',Now()) - interval '30 days'", nativeQuery = true)Integer selectmonthEnters(Integer id);
    @Query(value = "SELECT sum(time_table.questions) FROM time_table WHERE user_id=?1 AND time_table.date >= date_trunc('Day',Now()) - interval '30 days'", nativeQuery = true)Integer amountquestionsmonth(Integer id);
    @Query(value = "SELECT sum(time_table.corect) FROM time_table WHERE user_id=?1 AND time_table.date >= date_trunc('Day',Now()) - interval '30 days' ", nativeQuery = true)Integer amountquestionscorectmonth(Integer id);



    //for last year
    @Query(value = "SELECT SUM(avg )/COUNT(avg) FROM time_table WHERE user_id=?1 AND time_table.event='Exit' AND time_table.date >= date_trunc('Day',Now()) - interval '360 days' ", nativeQuery = true)Double selectyearAvg(Integer id);
    @Query(value = "SELECT max(time_table.max) FROM time_table WHERE user_id=?1 AND time_table.max!=0 AND time_table.date >= date_trunc('Day',Now()) - interval '360 days' ", nativeQuery = true)Integer selectyearMax(Integer id);
    @Query(value = "SELECT min(time_table.min) FROM time_table WHERE user_id=?1 AND time_table.min!=0  AND time_table.date >= date_trunc('Day',Now()) - interval '360 days'", nativeQuery = true)Integer selectyearMin(Integer id);
    @Query(value = "SELECT count(time_table.event) FROM time_table WHERE user_id=?1 AND time_table.event='Enter in system' AND time_table.date >= date_trunc('Day',Now()) - interval '360 days'", nativeQuery = true)Integer selectyearEnters(Integer id);
    @Query(value = "SELECT sum(time_table.questions) FROM time_table WHERE user_id=?1 AND time_table.date >= date_trunc('Day',Now()) - interval '360 days'", nativeQuery = true)Integer amountquestionsyear(Integer id);
    @Query(value = "SELECT sum(time_table.corect) FROM time_table WHERE user_id=?1 AND time_table.date >= date_trunc('Day',Now()) - interval '360 days' ", nativeQuery = true)Integer amountquestionscorectyear(Integer id);



}
