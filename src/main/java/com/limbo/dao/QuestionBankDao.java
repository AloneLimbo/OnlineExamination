package com.limbo.dao;

import com.limbo.dao.provider.QuestionBankSqlProvider;
import com.limbo.entity.QuestionBank;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 试题库数据操作
 * Created by limbo on 17-4-8.
 */
@Mapper
public interface QuestionBankDao {

    @Select("SELECT 1 FROM question_bank WHERE question_bank_name = #{question_bank_name} AND founder_id=#{founder_id} LIMIT 1")
    String questionBankNameExists(@Param("question_bank_name") String questionBankName, @Param("founder_id") String founderId);

    @Insert("INSERT INTO question_bank(question_bank_id, question_bank_name, founder_id)\n" +
            "  VALUE(uuid(),#{question_bank_name},#{founder_id})")
    int insertQuestionBank(QuestionBank questionBank);


    @Select("SELECT question_bank_state,question_bank_id,question_bank_name,question_bank_create_date,u.real_name FROM question_bank q JOIN user u\n" +
            " ON q.founder_id = u.user_id AND q.founder_id=#{founder_id} AND q.question_bank_state=1 ORDER BY question_bank_create_date DESC LIMIT #{offset},8")
    List<QuestionBank> queryQuestionBanksByFounderId(@Param("founder_id") String founderId, @Param("offset") int offset);


    @Select("SELECT count(question_bank_id) FROM question_bank WHERE founder_id=#{founder_id};")
    int queryCountByFounderId(@Param("founder_id") String founderId);


    @Select("SELECT question_bank_id,question_bank_name FROM question_bank\n" +
            "WHERE question_bank_name LIKE #{search} AND founder_id=#{founderId} ORDER BY question_bank_create_date DESC  LIMIT 0,4")
    List<QuestionBank> searchQuestionBank(@Param("search") String search, @Param("founderId") String founder);


    @Select("select count(question_id) from question where question_bank_id=#{questionBankId}")
    int queryQuestionNumByQuestionBankId(@Param("questionBankId") String questionBankId);

    @Select("select count(question_id) from question where question_bank_id=#{questionBankId} AND is_what=1")
    int querySingleQuestionNumByQuestionBankId(@Param("questionBankId") String questionBankId);

    @Select("select count(question_id) from question where question_bank_id=#{questionBankId} AND is_what=2")
    int queryTFNumByQuestionBankId(@Param("questionBankId") String questionBankId);


    @Select("select count(question_id) from question where question_bank_id=#{questionBankId} AND is_what=3")
    int querySubjectiveQuestionNumByQuestionBankId(@Param("questionBankId") String questionBankId);

    @UpdateProvider(type =  QuestionBankSqlProvider.class,
            method = "updateQuestionNum")
    int updateQuestionBankQuestionNum(@Param("question_bank_id") String questionBankId, int isWhat);

    @Delete("DELETE FROM question_bank WHERE question_bank_id=#{questionBankId} AND founder_id=#{founderId}")
    int deleteQuestionBankById(@Param("questionBankId") String questionBankId, @Param("founderId") String founderId);
}
