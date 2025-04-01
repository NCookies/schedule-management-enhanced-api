package xyz.ncookie.sma.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String status;
    private int code;
    private String message;
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object detail;

    // ObejctMapper로 직렬화 했을 때의 포맷 지정
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return of(errorCode.getStatus(), errorCode.getMessage(), path, null);
    }

    public static ErrorResponse of(HttpStatus httpStatus, String message, String path, Object detail) {
        return new ErrorResponse(
                httpStatus.name(),
                httpStatus.value(),
                message,
                path,
                detail,
                LocalDateTime.now()
        );
    }

}
