package com.limbo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * 部门表实体类
 * Created by limbo on 17-4-5.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Branch {
    private String branch_id;
    private String branch_name;
    private Date branch_create_date;
    private String founder_id;
    private boolean branch_state;
    private String real_name;

    private int peopleNum;

    private int notCorrectedNum;

    public int getNotCorrectedNum() {
        return notCorrectedNum;
    }

    public void setNotCorrectedNum(int notCorrectedNum) {
        this.notCorrectedNum = notCorrectedNum;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }


    public Date getBranch_create_date() {
        return branch_create_date;
    }

    public void setBranch_create_date(Date branch_create_date) {
        this.branch_create_date = branch_create_date;
    }

    public String getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(String founder_id) {
        this.founder_id = founder_id;
    }

    public boolean isBranch_state() {
        return branch_state;
    }

    public void setBranch_state(boolean branch_state) {
        this.branch_state = branch_state;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branch_id='" + branch_id + '\'' +
                ", branch_name='" + branch_name + '\'' +
                ", branch_create_date=" + branch_create_date +
                ", founder_id='" + founder_id + '\'' +
                ", branch_state=" + branch_state +
                ", real_name='" + real_name + '\'' +
                ", peopleNum=" + peopleNum +
                '}';
    }
}
