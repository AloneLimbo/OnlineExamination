package com.limbo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.limbo.util.JacksonUtil;

import java.io.IOException;
import java.util.Date;

/**
 * 试卷表试题类
 * Created by limbo on 17-4-17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Paper {

    private String paper_id;

    private String paper_name;

    private Object paper_question;

    private String founder_id;

    private Date paper_create_date;

    private int paper_score;

    private boolean isFromDataBase=true;

    private String real_name;

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


    public int getPaper_score() {
        return paper_score;
    }

    public void setPaper_score(int paper_score) {
        this.paper_score = paper_score;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public Object getPaper_question() {

        if(!this.isFromDataBase){
                try {
                    return JacksonUtil.fullDataConversion(this.paper_question);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
        }else {
            return this.paper_question;
        }
    }

    public void setPaper_question(Object paper_question) {
        if(paper_question instanceof String){
            try {
                this.paper_question=JacksonUtil.fullDataBinding((String) paper_question,Object.class);
            } catch (IOException e) {
                this.paper_question=paper_question;
            }
        }else {
            this.paper_question = paper_question;
        }
    }

    public String getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(String founder_id) {
        this.founder_id = founder_id;
    }

    public Date getPaper_create_date() {
        return paper_create_date;
    }

    public void setPaper_create_date(Date paper_create_date) {
        this.paper_create_date = paper_create_date;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "paper_id='" + paper_id + '\'' +
                ", paper_name='" + paper_name + '\'' +
                ", paper_question=" + paper_question +
                ", founder_id='" + founder_id + '\'' +
                ", paper_create_date=" + paper_create_date +
                ", paper_score=" + paper_score +
                ", isFromDataBase=" + isFromDataBase +
                '}';
    }
}
