package xyz.ncookie.sma.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ncookie.sma.member.dto.request.LoginRequestDto;
import xyz.ncookie.sma.member.service.AuthService;
import xyz.ncookie.sma.global.data.SessionConst;
import xyz.ncookie.sma.member.dto.response.MemberResponseDto;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> login(
            @RequestBody LoginRequestDto dto,
            HttpServletRequest request
    ) {

        MemberResponseDto responseDto = authService.login(dto);

        request.getSession(true).setAttribute(SessionConst.LOGIN_USER, responseDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        // 로그인 하지 않았던 경우 null 반환
        HttpSession session = request.getSession(false);

        // 로그인이 되있던 경우 (세션이 존재)
        if (session != null) {
            // 해당 세션(데이터)를 삭제
            session.invalidate();
        }
        
        return ResponseEntity.ok().build();
    }

}
