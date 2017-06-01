package com.limbo.dao;

import com.limbo.dao.provider.QuestionSqlProvider;
import com.limbo.dto.QuestionTemp;
import com.limbo.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 试题数据操作
 * Created by limbo on 17-4-8.
 */
@Mapper
public interface QuestionDao {

    @Select("SELECT question_state,question_id,question_title,question_create_date,real_name,is_what,question_level from question q join user u on " +
            "question_bank_id=#{questionBankId} and u.user_id=q.founder_id  ORDER BY question_create_date DESC LIMIT #{offset},8")
    List<Question> queryQuestionByQBId(@Param("questionBankId") String questionBankId, @Param("offset") int offset);

    @Insert("INSERT INTO question(question_id, question_title, answer, is_what, question_bank_id, founder_id,question_level,question_analysis) \n" +
            "  VALUE(uuid(),#{question_title},#{answer},#{is_what},#{question_bank_id},#{founder_id},#{question_level},#{question_analysis})")
    int insertQuestion(Question question);

    @Select("SELECT question_state,question_id,question_title,question_create_date,real_name,is_what,question_level,answer from question q join user u on " +
            "question_bank_id=#{questionBankId} and u.user_id=q.founder_id and is_what=#{isWhat} ORDER BY question_create_date DESC LIMIT #{offset},5")
    List<Question> queryQuestionByIsWhat(@Param("questionBankId") String questionBankId, @Param("offset") int offset, @Param("isWhat") int isWhat);

    @SelectProvider(type = QuestionSqlProvider.class
    ,method="queryQuestionById")
    List<Question> queryQuestionByQuestionId(@Param("questionId") List<QuestionTemp> questions);

    @Delete("DELETE FROM question WHERE question_id=#{questionId} AND founder_id=#{founderId};")
    int deleteQuestion(@Param("questionId") String questionId, @Param("founderId") String founderId);
}
