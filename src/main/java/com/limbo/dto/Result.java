package com.limbo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Http 返回对象实体
 * Created by limbo on 17-3-15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    private int code;

    private String msg;

    private long date;

    private Object data;

    public Result() {
    }

    public Result(int code, String msg, long date, Object data) {
        this.code = code;
        this.msg = msg;
        this.date = date;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", time=" + date +
                ", data=" + data +
                '}';
    }
}
