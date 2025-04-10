package xyz.ncookie.sma.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object detail;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = null;
    }

    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = customMessage;
    }

}
