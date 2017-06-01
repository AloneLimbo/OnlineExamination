package com.limbo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.limbo.util.JacksonUtil;

import java.io.IOException;

/**
 *
 * Created by limbo on 17-5-9.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionTemp {

    private String question_id;

    private String examinee_answer;

    private String answer;

    private int correctScore;

    private int score;

    private Object question_title;

    private String exam_id;

    private String founder_id;

    private int is_what;

    public int getCorrectScore() {
        return correctScore;
    }

    public void setCorrectScore(int correctScore) {
        this.correctScore = correctScore;
    }

    public int getIs_what() {
        return is_what;
    }

    public void setIs_what(int is_what) {
        this.is_what = is_what;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Object getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(Object question_title) {
        if(question_title instanceof String){
            try {
                this.question_title=JacksonUtil.fullDataBinding((String) question_title,Object.class);
            } catch (IOException e) {
                this.question_title=question_title;
            }
        }else {
            this.question_title = question_title;
        }
    }

    public String getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(String founder_id) {
        this.founder_id = founder_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }


    public String getExaminee_answer() {
        return examinee_answer;
    }

    public void setExaminee_answer(String examinee_answer) {
        this.examinee_answer = examinee_answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    @Override
    public String toString() {
        return "QuestionTemp{" +
                "question_id='" + question_id + '\'' +
                ", examinee_answer='" + examinee_answer + '\'' +
                ", answer='" + answer + '\'' +
                ", correctScore=" + correctScore +
                ", score=" + score +
                ", question_title=" + question_title +
                ", exam_id='" + exam_id + '\'' +
                ", founder_id='" + founder_id + '\'' +
                ", is_what=" + is_what +
                '}';
    }
}
