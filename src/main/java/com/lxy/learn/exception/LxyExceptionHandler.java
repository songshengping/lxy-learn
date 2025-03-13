package com.lxy.learn.exception;


import com.lxy.learn.enums.ErrorCode;
import com.lxy.learn.response.Response;
import com.lxy.learn.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 86185
 */
@RestControllerAdvice
@Slf4j
public class LxyExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException", ex);
        return new Response<>(ErrorCode.PARAM_ERROR, CommonUtils.exceptionStackTrace(ex));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Response<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException", ex);
        return new Response<>(ErrorCode.PARAM_ERROR, CommonUtils.exceptionStackTrace(ex));
    }

    @ExceptionHandler(IllegalStateException.class)
    public Response<Object> handleIllegalStateException(IllegalStateException ex) {
        log.error("IllegalStateException", ex);
        return new Response<>(ErrorCode.ILLEGAL_STATE, CommonUtils.exceptionStackTrace(ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException", ex);
        Response<Object> vo = new Response<>();
        vo.setSuccess(false);
        vo.setCode(ErrorCode.PARAM_ERROR.getCode());
        vo.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
        return vo;
    }

    @ExceptionHandler(LxyBusinessException.class)
    public Response<Object> handleIsmBusinessException(LxyBusinessException e) {
        log.error("ProbeBusinessException", e);
        return new Response<>(e.getCode(), e.getErrorMsg());
    }


    @ExceptionHandler(BindException.class)
    public Response<Object> handleBindException(BindException e) {
        log.error("BindException", e);
        return new Response<>(ErrorCode.PARAM_ERROR, e.getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public Response<Object> handleAllException(Exception ex) {
        log.error("Exception", ex);
        return new Response<>(ErrorCode.UNKNOWN_ERROR, "请联系管理员");
    }
}
