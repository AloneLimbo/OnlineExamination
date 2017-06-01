package com.limbo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.limbo.util.JacksonUtil;

import java.io.IOException;
import java.util.Date;

/**
 * 试题实体
 * Created by limbo on 17-4-8.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {

    private int is_what;
    private String question_id;
    private Object question_title;
    private String answer;
    private Date question_create_date;
    private boolean question_state;
    private String question_bank_id;
    private String founder_id;
    private boolean isFromDataBase=true;
    private String real_name;
    private int question_level;
    private String question_analysis;


    public String getQuestion_analysis() {
        return question_analysis;
    }

    public void setQuestion_analysis(String question_analysis) {
        this.question_analysis = question_analysis;
    }

    public int getQuestion_level() {
        return question_level;
    }

    public void setQuestion_level(int question_level) {
        this.question_level = question_level;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public boolean isFromDataBase() {
        return isFromDataBase;
    }

    public void setFromDataBase(boolean fromDataBase) {
        isFromDataBase = fromDataBase;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public int getIs_what() {
        return is_what;
    }

    public void setIs_what(int is_what) {
        this.is_what = is_what;
    }

    public Object getQuestion_title() {
        if(!this.isFromDataBase){
            if(this.question_title instanceof String ){
                return this.question_title;
            }else {
                try {
                    return JacksonUtil.fullDataConversion(this.question_title);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }else {
            return this.question_title;
        }
    }

    public void setQuestion_title(Object question_title) {
        if(question_title instanceof String){
            try {
                this.question_title= JacksonUtil.fullDataBinding((String) question_title,Object.class);
            } catch (IOException e) {
                this.question_title=question_title;
            }
        }else {
            this.question_title = question_title;
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getQuestion_create_date() {
        return question_create_date;
    }

    public void setQuestion_create_date(Date question_create_date) {
        this.question_create_date = question_create_date;
    }

    public boolean isQuestion_state() {
        return question_state;
    }

    public void setQuestion_state(boolean question_state) {
        this.question_state = question_state;
    }

    public String getQuestion_bank_id() {
        return question_bank_id;
    }

    public void setQuestion_bank_id(String question_bank_id) {
        this.question_bank_id = question_bank_id;
    }

    public String getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(String founder_id) {
        this.founder_id = founder_id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "is_what=" + is_what +
                ", question_id='" + question_id + '\'' +
                ", question_title=" + question_title +
                ", answer='" + answer + '\'' +
                ", question_create_date=" + question_create_date +
                ", question_state=" + question_state +
                ", question_bank_id='" + question_bank_id + '\'' +
                ", founder_id='" + founder_id + '\'' +
                ", isFromDataBase=" + isFromDataBase +
                ", real_name='" + real_name + '\'' +
                ", question_level=" + question_level +
                ", question_analysis='" + question_analysis + '\'' +
                '}';
    }
}
