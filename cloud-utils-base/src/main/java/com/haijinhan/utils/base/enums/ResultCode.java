package com.haijinhan.utils.base.enums;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "服务器内部错误"),
    VALIDATE_FAILED(400, "参数校验失败"),
    UNAUTHORIZED(401, "未授权或Token已过期"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "资源不存在"),
    PARAM_EXCEPTION(405, "参数错误导致异常"),
    RATE_LIMIT(429, "请求过于频繁，请稍后再试");

    private final int code;
    private final String message;
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
