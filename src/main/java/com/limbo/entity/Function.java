package com.limbo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * Created by limbo on 17-4-7.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Function {

    private String function_name;

    private String function_id;

    private String function_pid;

    private String function_url;

    private String is_parent;

    private String function_icon;

    private String pid;

    public String getFunction_url() {
        return function_url;
    }

    public void setFunction_url(String function_url) {
        this.function_url = function_url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFunction_name() {
        return function_name;
    }

    public void setFunction_name(String function_name) {
        this.function_name = function_name;
    }

    public String getFunction_id() {
        return function_id;
    }

    public void setFunction_id(String function_id) {
        this.function_id = function_id;
    }

    public String getFunction_pid() {
        return function_pid;
    }

    public void setFunction_pid(String function_pid) {
        this.function_pid = function_pid;
    }

    public String getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(String is_parent) {
        this.is_parent = is_parent;
    }

    public String getFunction_icon() {
        return function_icon;
    }

    public void setFunction_icon(String function_icon) {
        this.function_icon = function_icon;
    }

    @Override
    public String toString() {
        return "Function{" +
                "function_name='" + function_name + '\'' +
                ", function_id='" + function_id + '\'' +
                ", function_pid='" + function_pid + '\'' +
                ", function_url='" + function_url + '\'' +
                ", is_parent='" + is_parent + '\'' +
                ", function_icon='" + function_icon + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}
