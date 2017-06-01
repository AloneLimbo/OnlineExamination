package com.limbo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 考试表试题类
 * Created by limbo on 17-4-18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exam {

    private String exam_id;

    private String exam_name;

    private Date exam_start_date;

    private Date exam_end_date;

    private Date exam_create_date;

    private String paper_id;

    private String founder_id;

    private boolean exam_state;

    private String[] branchId;

    private int exam_time;

    public int getExam_time() {
        return exam_time;
    }

    public void setExam_time(int exam_time) {
        this.exam_time = exam_time;
    }

    public String[] getBranchId() {
        return branchId;
    }

    public void setBranchId(String[] branchId) {
        this.branchId = branchId;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public Date getExam_start_date() {
        return exam_start_date;
    }

    public void setExam_start_date(Object exam_start_date) {
        if(exam_start_date instanceof String)
        {
          this.exam_start_date = parseDate((String) exam_start_date);
          return;
        }
        this.exam_start_date = (Date) exam_start_date;
    }

    public Date getExam_end_date() {
        return exam_end_date;
    }

    public void setExam_end_date(Object exam_end_date) {
        if(exam_end_date instanceof String)
        {
           this.exam_end_date=parseDate((String) exam_end_date);
           return;
        }
        this.exam_end_date = (Date) exam_end_date;
    }

    public Date getExam_create_date() {
        return exam_create_date;
    }

    public void setExam_create_date(Date exam_create_date) {
        this.exam_create_date = exam_create_date;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(String founder_id) {
        this.founder_id = founder_id;
    }

    public boolean isExam_state() {
        return this.exam_state;
    }

    public void setExam_state(boolean exam_state) {
        this.exam_state = exam_state;
    }


    private Date parseDate(String data){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return simpleDateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "exam_id='" + exam_id + '\'' +
                ", exam_name='" + exam_name + '\'' +
                ", exam_start_date=" + exam_start_date +
                ", exam_end_date=" + exam_end_date +
                ", exam_create_date=" + exam_create_date +
                ", paper_id='" + paper_id + '\'' +
                ", founder_id='" + founder_id + '\'' +
                ", exam_state=" + exam_state +
                ", branchId=" + Arrays.toString(branchId) +
                '}';
    }
}
