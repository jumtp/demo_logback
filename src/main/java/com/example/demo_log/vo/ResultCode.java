package com.example.demo_log.vo;

public enum ResultCode {

    SUCCESS(200, "请求成功"),
    FAILED(300, "请求失败"),
    FAILED_SELECT_NULL(301,"查询数据为空"),
    VALIDATE_ERROR(400, "参数校验失败"),
    PAGE_NOT_FOUND(404,"页面不存在"),
    RESPONSE_PACK_ERROR(500, "response返回包装失败");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
