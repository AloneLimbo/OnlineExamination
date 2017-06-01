package com.limbo.dao;

import com.limbo.entity.ReportCard;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 错题本DAO
 * Created by limbo on 17-5-9.
 */
@Mapper
public interface ReportCardDao {

    @Insert("INSERT INTO report_card(report_card_id, score, founder_id, exam_id,state)\n" +
            "  VALUE(uuid(),#{score},#{founder_id},#{exam_id},#{state});")
    int insert(ReportCard reportCard);

    @Select("SELECT score,e.exam_name,rc.report_card_create_date,u.real_name FROM report_card rc JOIN exam e JOIN user u\n" +
            "ON rc.founder_id=#{founderId} AND rc.exam_id = e.exam_id AND rc.state=1 AND u.user_id=rc.founder_id;")
    List<ReportCard> queryReportCard(@Param("founderId") String founderId);

    @Update("UPDATE report_card SET score=(SELECT history_exam.exam_score FROM history_exam WHERE history_exam.founder_id =#{founderId} AND history_exam.exam_id=#{examId})+#{score},state=1 " +
            "where exam_id=#{examId} AND founder_id=#{founderId}")
    int updateScore(@Param("score") int score, @Param("examId") String examId, @Param("founderId") String founderId);

}
