package com.limbo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.limbo.dto.QuestionTemp;
import com.limbo.util.JacksonUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by limbo on 17-5-9.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryExam {

    private String id;

    private int exam_score;

    private String exam_id;

    private int state;

    private String founder_id;

    private Date create_date;

    private String autoScoring_questions;

    private String subjective_questions;

    private List<QuestionTemp> autoScoringQuestions;

    private List<QuestionTemp> subjectiveQuestions;

    public String getAutoScoring_questions() {
        return autoScoring_questions;
    }

    public void setAutoScoring_questions(String autoScoring_questions) {
        this.autoScoring_questions = autoScoring_questions;
        try {
            this.autoScoringQuestions = JacksonUtil.fullDataBinding(autoScoring_questions, autoScoringQuestions.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSubjective_questions() {
        return subjective_questions;
    }

    public void setSubjective_questions(String subjective_questions) {
        if(subjective_questions!=null) {
            this.subjective_questions = subjective_questions;
            try {
                this.subjectiveQuestions = (List<QuestionTemp>) JacksonUtil.fullDataBinding(this.subjective_questions, Object.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            this.subjective_questions = null;
        }
    }

    public List<QuestionTemp> getAutoScoringQuestions() {
        return autoScoringQuestions;
    }

    public void setAutoScoringQuestions(List<QuestionTemp> autoScoringQuestions) {
        this.autoScoringQuestions = autoScoringQuestions;
        try {
            this.autoScoring_questions = JacksonUtil.fullDataConversion(autoScoringQuestions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<QuestionTemp> getSubjectiveQuestions() {
        return subjectiveQuestions;
    }

    public void setSubjectiveQuestions(List<QuestionTemp> subjectiveQuestions) {
        this.subjectiveQuestions = subjectiveQuestions;
        try {
            this.subjective_questions = JacksonUtil.fullDataConversion(subjectiveQuestions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getExam_score() {
        return exam_score;
    }

    public void setExam_score(int exam_score) {
        this.exam_score = exam_score;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(String founder_id) {
        this.founder_id = founder_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }


    @Override
    public String toString() {
        return "HistoryExam{" +
                "id='" + id + '\'' +
                ", exam_score=" + exam_score +
                ", exam_id='" + exam_id + '\'' +
                ", state=" + state +
                ", founder_id='" + founder_id + '\'' +
                ", create_date=" + create_date +
                ", autoScoring_questions='" + autoScoring_questions + '\'' +
                ", subjective_questions='" + subjective_questions + '\'' +
                ", autoScoringQuestions=" + autoScoringQuestions +
                ", subjectiveQuestions=" + subjectiveQuestions +
                '}';
    }
}
