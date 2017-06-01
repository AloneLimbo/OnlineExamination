package com.limbo.dao.provider;

import com.limbo.dto.QuestionTemp;

import java.util.List;

/**
 *
 * Created by limbo on 17-4-19.
 */
public class QuestionSqlProvider {

    public String queryQuestionById(final List<QuestionTemp> questions){

        StringBuilder sql = new StringBuilder("SELECT question_id,answer from question where ");

        for(int i=0; i<questions.size();i++){
            if(i==(questions.size()-1)){
                sql.append("question_id='").append(questions.get(i).getQuestion_id()).append("'").append(" ;");

            }else {
                sql.append("question_id='").append(questions.get(i).getQuestion_id()).append("'").append(" or ");
            }
        }

        return sql.toString();
    }
}
