package com.cc.ata.constant;

import lombok.Data;

/**
 * @author cc
 */
@Data
public class RestBean {
    private int code;

    private String msg;

    private int count;

    private Object data;



    public RestBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestBean(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RestBean(int code, String msg, int total, Object data) {
        this.code = code;
        this.msg = msg;
        this.count=total;
        this.data = data;
    }
}
