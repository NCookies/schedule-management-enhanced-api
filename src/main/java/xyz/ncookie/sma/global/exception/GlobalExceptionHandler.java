package xyz.ncookie.sma.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        log.warn("@Valid 요청 파라미터 유효성 검사 실패: {}", message);

        return getErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    // 커스텀 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        log.warn("[{}] 예외 발생: {}", ex.getClass().getSimpleName(), ex.getMessage());

        return getErrorResponse(ex.getHttpStatus(), ex.getMessage());
    }

    // @ExceptionHandler는 더 구체적인 예외 타입이 우선적으로 매칭되므로, 여기에는 미처 핸들링하지 못한 예외들이 들어온다.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnhandledException(Exception ex) {
        // 에러 메세지 + 스택 트레이스
        log.error("핸들링되지 않은 예외 발생!!! : [{}]{}", ex.getClass().getSimpleName(), ex.getMessage(), ex);

        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> getErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.name());
        errorResponse.put("code", status.value());
        errorResponse.put("message", message);

        return new ResponseEntity<>(errorResponse, status);
    }

}
