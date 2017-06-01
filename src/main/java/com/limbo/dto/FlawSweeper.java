package com.limbo.dto;

/**
 * 错题本实体类
 * Created by limbo on 17-5-9.
 */
public class FlawSweeper {


    private String exam_id;

    private String exam_name;

    private int wrong_num;

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

    public int getWrong_num() {
        return wrong_num;
    }

    public void setWrong_num(int wrong_num) {
        this.wrong_num = wrong_num;
    }

    @Override
    public String toString() {
        return "FlawSweeper{" +
                "exam_id='" + exam_id + '\'' +
                ", exam_name='" + exam_name + '\'' +
                ", wrong_num=" + wrong_num +
                '}';
    }
}
