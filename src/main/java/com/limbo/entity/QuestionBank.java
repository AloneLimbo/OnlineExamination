package com.limbo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * 试题库实体
 * Created by limbo on 17-4-8.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionBank {

    private String question_bank_id;
    private String question_bank_name;
    private Date question_bank_create_date;
    private String founder_id;
    private boolean question_bank_state;

    private String real_name;
    private int questionType;

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }


    public String getQuestion_bank_id() {
        return question_bank_id;
    }

    public void setQuestion_bank_id(String question_bank_id) {
        this.question_bank_id = question_bank_id;
    }

    public String getQuestion_bank_name() {
        return question_bank_name;
    }

    public void setQuestion_bank_name(String question_bank_name) {
        this.question_bank_name = question_bank_name;
    }

    public Date getQuestion_bank_create_date() {
        return question_bank_create_date;
    }

    public void setQuestion_bank_create_date(Date question_bank_create_date) {
        this.question_bank_create_date = question_bank_create_date;
    }

    public String getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(String founder_id) {
        this.founder_id = founder_id;
    }

    public boolean isQuestion_bank_state() {
        return question_bank_state;
    }

    public void setQuestion_bank_state(boolean question_bank_state) {
        this.question_bank_state = question_bank_state;
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "question_bank_id='" + question_bank_id + '\'' +
                ", question_bank_name='" + question_bank_name + '\'' +
                ", question_bank_create_date=" + question_bank_create_date +
                ", founder_id='" + founder_id + '\'' +
                ", question_bank_state=" + question_bank_state +
                ", real_name='" + real_name + '\'' +
                ", questionType=" + questionType +
                '}';
    }
}
