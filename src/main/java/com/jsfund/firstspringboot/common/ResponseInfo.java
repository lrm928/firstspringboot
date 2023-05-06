package com.jsfund.firstspringboot.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 响应数据封装：统一的 JSON 结构中属性包括数据、状态码、提示信息
 * @author Administrator
 * @create 2023/4/30 15:39
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo<T> {

    private static final int DEFAULT_SUCCESS_CODE = 0;
    private static final String DEFAULT_SUCCESS_MSG = "操作成功！";

    /**
     * 状态码
     */
    protected Integer code;
    /**
     * 响应信息
     */
    protected String msg;
    /**
     * 返回数据
     */
    private T data;

    /**
     * 若没有数据返回，默认状态码为 0，提示信息为“操作成功！”
     */
    public ResponseInfo() {
        this.code = DEFAULT_SUCCESS_CODE;
        this.msg = DEFAULT_SUCCESS_MSG;
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     */
    public ResponseInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为 0，默认提示信息为“操作成功！”
     */
    public ResponseInfo(T data) {
        this.code = DEFAULT_SUCCESS_CODE;
        this.msg = DEFAULT_SUCCESS_MSG;
        this.data = data;
    }
}
