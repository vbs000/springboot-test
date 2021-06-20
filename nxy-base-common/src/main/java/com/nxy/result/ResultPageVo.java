package com.nxy.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 带参数返回值封装
 * @param <T>
 */

public class ResultPageVo<T> {
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 页数
     */
    private int pageSize;
    /**
     * 总记录数量
     */
    private int total;
    /**
     * 返回的数据
     */
    private T data;

    public ResultPageVo(String msg, int code, int pageNum, int pageSize, int total, T data) {
        this.msg = msg;
        this.code = code;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
