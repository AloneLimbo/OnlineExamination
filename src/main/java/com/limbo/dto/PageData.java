package com.limbo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 分页数据封装
 * Created by limbo on 17-4-13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageData {

    private int totalItems;

    private Object data;

    public PageData() {
    }

    public PageData(int totalItems, Object data) {
        this.totalItems = totalItems;
        this.data = data;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "totalItems=" + totalItems +
                ", data=" + data +
                '}';
    }
}
