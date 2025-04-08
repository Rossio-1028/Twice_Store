package com.shanzhu.st.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shanzhu.st.enums.ErrorMsg;

/**
 * 前端接口返回包装模型
 *
 *
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class R<T> {

    private Integer status_code;

    private String msg;

    private T data;

    public static R success() {
        R r = new R();
        r.setStatus_code(1);
        return r;
    }

    public static <T> R success(T data) {
        R<T> r = new R<>();
        r.setStatus_code(1);
        r.setData(data);
        return r;
    }

    public static R fail(ErrorMsg errorMsg) {
        R r = new R();
        r.setStatus_code(0);
        r.setMsg(errorMsg.getMsg());
        return r;
    }

    public static <T> R fail(ErrorMsg errorMsg, T data) {
        R<T> r = new R<>();
        r.setStatus_code(0);
        r.setMsg(errorMsg.getMsg());
        r.setData(data);
        return r;
    }


    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public R(Integer status_code, String msg, T data) {
        this.status_code = status_code;
        this.msg = msg;
        this.data = data;
    }

    public R() {
    }
}
