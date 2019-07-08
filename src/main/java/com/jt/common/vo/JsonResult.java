package com.jt.common.vo;

import java.io.Serializable;

public class JsonResult implements Serializable {
    /**状态码*/
    private int state=1;//1表示成功，0表示错误
    /**状态信息*/
    private String message = "ok";
    /**正确数据*/
    private Object data;
    /**一般查询时调用，封装查询结果*/
    public JsonResult(Object data) {
        this.data = data;
    }

    public JsonResult(Throwable t) {
        this.state = 0;
        this.message=t.getMessage();
    }

    /**出现异常时调用*/



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
