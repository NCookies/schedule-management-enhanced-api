package xyz.ncookie.sma.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;
import xyz.ncookie.sma.global.data.SessionConst;
import xyz.ncookie.sma.global.exception.ErrorCode;
import xyz.ncookie.sma.global.exception.ErrorResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {
            "/api/members/register",
            "/api/auth/login",
            "/api/auth/logout"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        // WHITE LIST 외의 URI 대상 로직 실행
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
                log.debug("로그인 되지 않은 상태에서 서비스 접근: {}", requestURI);
                sendErrorResponse((HttpServletResponse) servletResponse, ErrorCode.LOGIN_REQUIRED, requestURI);
                return;
            }

            log.info("로그인에 성공했습니다.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestURI) {

        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode, String path) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());    // LocalDateTime 타입의 직렬화를 위해 모듈 등록해야 함

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(response.getStatus());

        try {
            response.getWriter().write(
                    mapper.writeValueAsString(ErrorResponse.of(errorCode, path))
            );
        } catch (IOException e) {
            log.error("LoginFilter 응답 생성 중 에러 발생");
            log.error("[{}]: {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }

}
