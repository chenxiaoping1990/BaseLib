package com.src.fookiiapp.model.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/2/10 0010.
 */

public class BaseEntity<T> implements Serializable {
    public int ret;
    public String msg;
    public T data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
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

}
