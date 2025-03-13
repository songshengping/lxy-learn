package com.lxy.learn.exception;

import com.lxy.learn.enums.ErrorCode;
import com.lxy.learn.utils.CommonUtils;
import lombok.Data;

import java.text.MessageFormat;

/**
 * @author 86185
 */
@Data
public class LxyBusinessException extends RuntimeException {
    private Integer code;
    private String errorMsg;

    public LxyBusinessException() {
        super();
    }

    public LxyBusinessException(String message) {
        super(message);
    }

    public LxyBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public LxyBusinessException(Throwable cause) {
        super(cause);
    }

    public LxyBusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }

    public LxyBusinessException(ErrorCode errorCode, String detailMessage, Object... args) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
        if (CommonUtils.isNotEmpty(detailMessage) && CommonUtils.isNotEmpty(args)) {
            this.errorMsg += ":" + MessageFormat.format(detailMessage, args);
        }

    }

    public LxyBusinessException(ErrorCode errorCode, String detailMessage) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
        if (CommonUtils.isNotEmpty(detailMessage)) {
            this.errorMsg += ":" + detailMessage;
        }

    }
}
