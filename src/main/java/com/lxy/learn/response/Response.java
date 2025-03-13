package com.lxy.learn.response;


import com.lxy.learn.enums.ErrorCode;
import com.lxy.learn.utils.CommonUtils;
import lombok.Data;

import java.text.MessageFormat;

/**
 * 公共响应类
 * @param <T>
 */
@Data
public class Response<T> {
    private Boolean success = true;

    private Integer code = 0;

    private String message = "";

    private T result;

    private Long timestamp;

    public Response() {

    }

    public Response(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timestamp = System.currentTimeMillis();
        this.success = false;
    }

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.success = false;
    }

    public Response(T result) {
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getMessage();
        this.timestamp = System.currentTimeMillis();
        this.success = true;
        this.result = result;
    }

    public Response(ErrorCode errorCode, String detailMessage, Object... args) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        if (CommonUtils.isNotEmpty(detailMessage) && CommonUtils.isNotEmpty(args)) {
            this.message += ":" + MessageFormat.format(detailMessage, args);
        }
        this.timestamp = System.currentTimeMillis();
        this.success = false;
    }


    public Response(ErrorCode errorCode, String detailMessage) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        if (CommonUtils.isNotEmpty(detailMessage)) {
            this.message += ":" + detailMessage;
        }
        this.timestamp = System.currentTimeMillis();
        this.success = false;
    }

    public static Response error(String s) {
        Response result = new Response();
        result.setSuccess(false);
        result.setCode(ErrorCode.ERROR.getCode());
        result.setMessage(s);
        return result;
    }

    public static Response ok(String s) {
        Response result = new Response();
        result.setSuccess(true);
        result.setCode(ErrorCode.SUCCESS.getCode());
        result.setMessage(s);
        return result;
    }

    public static <T> Response success(T t) {
        Response result = new Response();
        result.setSuccess(true);
        result.setCode(ErrorCode.SUCCESS.getCode());
        result.setMessage(ErrorCode.SUCCESS.getMessage());
        result.setResult(t);
        return result;
    }

    public void setResult(T result) {
        this.code = ErrorCode.SUCCESS.getCode();
        this.timestamp = System.currentTimeMillis();
        this.success = true;
        this.result = result;
    }
}
