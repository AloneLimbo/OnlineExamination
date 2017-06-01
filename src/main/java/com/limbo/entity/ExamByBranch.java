package com.limbo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * Created by limbo on 17-4-18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamByBranch {

    private String id;

    private String branch_id;

    private String exam_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    @Override
    public String toString() {
        return "ExambyBranch{" +
                "id='" + id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", exam_id='" + exam_id + '\'' +
                '}';
    }
}
