package com.limbo.dao;

import com.limbo.entity.Exam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * Created by limbo on 17-4-18.
 */
@Mapper
public interface ExamDao {


    @Insert("INSERT INTO exam(exam_id, exam_name, exam_start_date, exam_end_date, paper_id, founder_id,exam_time)\n" +
            "  VALUE (#{exam_id},#{exam_name},#{exam_start_date},#{exam_end_date},#{paper_id},#{founder_id},#{exam_time})")
    int insertExam(Exam exam);

    @Select("SELECT e.exam_state,e.exam_end_date,e.exam_start_date,e.exam_id,e.exam_name,e.exam_time,e.paper_id\n" +
            " FROM user u JOIN exam e JOIN exam_branch eb  ON u.user_id=#{userId} AND u.branch_id=eb.branch_id AND eb.exam_id=e.exam_id AND eb.exam_id NOT IN (SELECT exam_id FROM history_exam WHERE history_exam.founder_id = #{userId})")
    List<Exam> queryExamsByBranch(@Param("userId") String userId);

    @Select("SELECT exam_id,exam_name,exam_start_date,exam_end_date,exam_create_date,exam_state FROM exam WHERE founder_id=#{founderId}")
    List<Exam> queryExamInfoByFounderId(@Param("founderId") String founderId);
}
