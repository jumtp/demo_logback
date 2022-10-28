package com.example.demo_log.exception;


import com.example.demo_log.vo.ResultCode;

public class APIException extends RuntimeException{
    private final int code;
    private final String msg;

    public APIException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
