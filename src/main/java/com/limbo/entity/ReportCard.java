package com.limbo.entity;

import java.util.Date;

/**
 * 成绩表
 * Created by limbo on 17-5-9.
 */
public class ReportCard {

    private String report_card_id;

    private int score;

    private String rank;

    private String founder_id;

    private Date report_card_create_date;

    private String exam_id;

    private int state;

    private String real_name;

    private String exam_name;

    public ReportCard() {
    }

    public ReportCard(int score, String founder_id, String exam_id, int state) {
        this.score = score;
        this.founder_id = founder_id;
        this.exam_id = exam_id;
        this.state = state;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(String founder_id) {
        this.founder_id = founder_id;
    }

    public String getReport_card_id() {
        return report_card_id;
    }

    public void setReport_card_id(String report_card_id) {
        this.report_card_id = report_card_id;
    }

    public Date getReport_card_create_date() {
        return report_card_create_date;
    }

    public void setReport_card_create_date(Date report_card_create_date) {
        this.report_card_create_date = report_card_create_date;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "report_card_id='" + report_card_id + '\'' +
                ", score=" + score +
                ", rank='" + rank + '\'' +
                ", founder_id='" + founder_id + '\'' +
                ", report_card_create_date=" + report_card_create_date +
                ", exam_id='" + exam_id + '\'' +
                ", state=" + state +
                ", real_name='" + real_name + '\'' +
                ", exam_name='" + exam_name + '\'' +
                '}';
    }
}
