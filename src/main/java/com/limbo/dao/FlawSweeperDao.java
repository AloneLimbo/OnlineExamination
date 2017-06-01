package com.limbo.dao;

import com.limbo.dto.FlawSweeper;
import com.limbo.dto.QuestionTemp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 错题本数据操作
 * Created by limbo on 17-5-9.
 */
@Mapper
public interface FlawSweeperDao {

    @Select("SELECT 1 FROM flaw_sweeper WHERE question_id = #{question_id} AND exam_id=#{exam_id} LIMIT 1")
    String questionExists(@Param("question_id") String question_id, @Param("exam_id") String exam_id);

    @Insert("INSERT INTO flaw_sweeper(id, founder_id, question_id, examinee_answer,exam_id) \n" +
            "  VALUE(uuid(),#{founder_id},#{question_id},#{examinee_answer},#{exam_id}) ")
    int insert(QuestionTemp wrongQuestion);

    @Update("UPDATE flaw_sweeper SET examinee_answer=#{examinee_answer} WHERE question_id=#{question_id}" +
            " AND exam_id=#{exam_id} and founder_id=#{founder_id}")
    int update(QuestionTemp wrongQuestion);

    @Select("SELECT DISTINCT f.exam_id,count(f.exam_id) AS wrong_num,e.exam_name FROM flaw_sweeper f JOIN exam e ON e.exam_id=f.exam_id AND f.founder_id=#{founderId} GROUP BY f.exam_id  LIMIT #{offset},8")
    List<FlawSweeper> queryFlawSweeperByFounderId(@Param("founderId") String founderId, @Param("offset") int offset);

    @Select("SELECT COUNT(DISTINCT exam_id)FROM flaw_sweeper WHERE founder_id=#{founderId} ORDER BY exam_id")
    int queryFlawSweeperNumByFounderId(@Param("founderId") String founderId);

    @Select("SELECT q.is_what,q.question_title,q.answer,f.examinee_answer FROM flaw_sweeper f JOIN question q ON f.exam_id=#{examId} AND f.founder_id=#{founderId} AND f.question_id = q.question_id;")
    List<QuestionTemp> getWrongQuestions(@Param("examId") String examId, @Param("founderId") String founderId);
}
