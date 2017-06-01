package com.limbo.dao;

import com.limbo.entity.HistoryExam;
import org.apache.ibatis.annotations.*;

/**
 *
 * Created by limbo on 17-5-9.
 */
@Mapper
public interface HistoryExamDao {

    @Insert("INSERT INTO history_exam(id, exam_score, exam_id, state, founder_id, autoScoring_questions, subjective_questions)\n" +
            " VALUE (uuid(),#{exam_score},#{exam_id},1,#{founder_id},#{autoScoring_questions},#{subjective_questions})")
    int insert(HistoryExam historyExam);

    @Select("SELECT exam_id,founder_id,subjective_questions\n" +
            "FROM history_exam WHERE founder_id = #{founderId} AND exam_id=#{examId};")
    HistoryExam querySubjectiveQuestions(@Param("examId") String examId, @Param("founderId") String founderId);

    @Update("update history_exam set subjective_questions=#{subjective_questions} where founder_id = #{founder_id} AND exam_id=#{exam_id}")
    int updateSubjectiveQuestions(HistoryExam historyExam);
}
