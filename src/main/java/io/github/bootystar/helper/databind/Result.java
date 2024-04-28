package io.github.bootystar.helper.databind;


import lombok.Data;

/**
 * 通用数据返回类型
 * @author booty
 */
@Data
public class Result<T> {
    public static final Integer UNAUTHORIZED = -1;
    public static final Integer SUCCESS = 1;
    public static final Integer FAILURE = 2;
    public static final Integer ERROR = 3;

    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS);
        return result;
    }

    public static <T> Result<T> failure(String msg) {
        Result<T> result = new Result<>();
        result.setCode(FAILURE);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> failure(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(FAILURE);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(ERROR);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> unauthorized(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(UNAUTHORIZED);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> unauthorized() {
        Result<T> result = new Result<>();
        result.setCode(UNAUTHORIZED);
        result.setMsg("权限不足");
        return result;
    }

}
