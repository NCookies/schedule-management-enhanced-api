package xyz.ncookie.sma.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
    }

    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(errorCode.getMessage() + customMessage);
        this.httpStatus = errorCode.getStatus();
    }

}
