package com.haijinhan.utils.base.result;

import com.haijinhan.utils.FormatUtils;
import com.haijinhan.utils.base.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    private long timestamp;
    private String dateTime;

    public Result() {
        this.timestamp = Instant.now().toEpochMilli();
        this.dateTime = FormatUtils.timestampToString(this.timestamp);
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCode.FAILED.getCode(), message, null);
    }

    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

}
