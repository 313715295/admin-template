package com.admin.commons.base;

import com.admin.commons.enums.ResponseEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 调用底层业务统一返回类型
 */
@Getter
@Setter
@Accessors(chain = true)
public class Result<T> implements Serializable {

    /**
     * 是否正确
     */
    private boolean result;
    /**
     * 消息
     */
    private String msg ;
    /**
     * 标识码
     */
    private int code;

    /**
     * 数据
     */
    private T data ;


    /**
     * 服务器出错
     */
    public static Result serverError() {
        return new Result(ResponseEnum.SERVICE_CRASH,false);
    }

    /**
     * 服务器禁止访问
     */
    public static Result fail() {
        return new Result(ResponseEnum.FAIL_CODE, false);
    }

    /**
     * 自定义禁止返回
     */
    public static Result fail(ResponseEnum responseEnum,boolean result) {
        return new Result(responseEnum, result);
    }

    //正确返回

    /**
     * 请求正确返回
     */
    public static Result success() {
        return new Result(ResponseEnum.SUCCESS,false);
    }

    /**
     * 请求正确返回数据
     */
    public static <T> Result<T> success(T date) {
        return new Result<>(ResponseEnum.SUCCESS,true, date);
    }

    /**
     * 请求正确返回消息
     */
    public static Result successMsg(String msg) {
        return new Result(ResponseEnum.SUCCESS,true, msg);
    }

    /**
     * 请求正确返回消息和数据
     */
    public static <T> Result<T> successMsg(String msg, T date) {
        return new Result<>(ResponseEnum.SUCCESS,true, msg, date);
    }

    //不正确返回

    /**
     * 请求不正确返回
     */
    public static Result error() {
        return new Result(ResponseEnum.ERROR,false);
    }

    /**
     * 请求不正确返回数据
     */
    public static <T> Result<T> error(T date) {
        return new Result<>(ResponseEnum.ERROR,false, date);
    }

    /**
     * 请求不正确返回消息
     */
    public static Result errorMsg(String msg) {
        return new Result(ResponseEnum.ERROR,false, msg);
    }

    /**
     * 请求不正确返回数据和消息
     */
    public static <T> Result<T> errorMsg(String msg, T date) {
        return new Result<>(ResponseEnum.ERROR,false, msg, date);
    }


    public Result() {
    }

    private Result(ResponseEnum responseEnum, boolean result) {
        this.msg = responseEnum.getMsg();
        this.code = responseEnum.getCode();
        this.result = result;
    }

    private Result(ResponseEnum responseEnum,boolean result, T data) {
        this.msg = responseEnum.getMsg();
        this.code = responseEnum.getCode();
        this.result = result;
        this.data = data;
    }

    private Result(ResponseEnum responseEnum,boolean result, String msg) {
        this.code = responseEnum.getCode();
        this.msg = msg;
        this.result = result;
    }

    private Result(ResponseEnum responseEnum,boolean result, String msg, T data) {
        this.code = responseEnum.getCode();
        this.msg = msg;
        this.result = result;
        this.data = data;
    }
}
