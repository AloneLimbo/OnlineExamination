package com.limbo.dao.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 *
 * Created by limbo on 17-4-16.
 */
public class QuestionBankSqlProvider {

    public String updateQuestionNum(final String question_bank_id,final int isWhat){
        return new SQL(){{
            UPDATE("question_bank");
            SET("question_num=question_num+1");
            if(isWhat==1){
                SET("single_question_num=single_question_num+1");
            }
            if(isWhat==2){
                SET("tf_num=tf_num+1");
            }
            if(isWhat==3){
                SET("subjective_question_num=subjective_question_num+1");
            }
            WHERE("question_bank_id=#{question_bank_id}");
        }}.toString();
    }

}
