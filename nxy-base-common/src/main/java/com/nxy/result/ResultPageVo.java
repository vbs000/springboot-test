package com.nxy.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 带参数返回值封装
 * @param <T>
 */
@Data
@AllArgsConstructor
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
}
