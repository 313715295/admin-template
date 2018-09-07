package com.admin.commons.enums;

/**
 * 返回枚举
 */
public enum ResponseEnum {

    SUCCESS(200," 请求正确"),
    ERROR(400,"请求不正确"),
    PAY_ERROR_CODE(405,"支付失败"),
    FAIL_CODE(500,"禁止访问"),
    SERVICE_CRASH(503,"服务器异常"),
    SERVICE_RETRY(504,"服务器连接失败"),
    TOKEN_EXPIRED(506,"token失效"),
    TOKEN_REPEAT(508,"异地登录"),
    PERMISSION_ERROR(509,"权限不够");


    //服务器获取验证码

    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
