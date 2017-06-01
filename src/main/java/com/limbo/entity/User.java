package com.limbo.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jxl.Cell;

import java.util.Date;

/**
 * 用户表实体类
 * Created by limbo on 17-4-5.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private String user_id;
    private String user_name;
    private String real_name;
    private String password;
    private int user_status;
    private boolean user_state;
    private String branch_id;
    private String branch_name;
    private Date user_create_date;
    private String email;

    private int state;

    private int score;

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

    public User(){

    }

    public User(Cell[] cells){
        this.user_name=cells[0].getContents();
        this.real_name=cells[1].getContents();
        this.password=cells[2].getContents();
        this.email=cells[3].getContents();
        this.branch_name=cells[4].getContents();
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public boolean isUser_state() {
        return user_state;
    }

    public void setUser_state(boolean user_state) {
        this.user_state = user_state;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public Date getUser_create_date() {
        return user_create_date;
    }

    public void setUser_create_date(Date user_create_date) {
        this.user_create_date = user_create_date;
    }



    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", real_name='" + real_name + '\'' +
                ", password='" + password + '\'' +
                ", user_status='" + user_status + '\'' +
                ", user_state=" + user_state +
                ", branch_id='" + branch_id + '\'' +
                ", user_create_date=" + user_create_date +
                '}';
    }

    public static void main(String[] args)
    {
//        System.out.println(UUID.randomUUID().toString());

        System.out.println(new Date().getTime());
    }


}
