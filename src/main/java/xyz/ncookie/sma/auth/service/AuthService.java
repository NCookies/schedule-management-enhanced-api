package xyz.ncookie.sma.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.ncookie.sma.auth.dto.request.LoginRequestDto;
import xyz.ncookie.sma.global.exception.LoginFailureException;
import xyz.ncookie.sma.member.dto.response.MemberResponseDto;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public MemberResponseDto login(LoginRequestDto dto) {

        Member findMember = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new LoginFailureException("존재하지 않는 이메일입니다."));

        // TODO: PasswordEncoder 사용 시 matches 로 변경
        if (!findMember.getPassword().equals(dto.password())) {
            throw new LoginFailureException("비밀번호가 일치하지 않습니다.");
        }

        return MemberResponseDto.fromEntity(findMember);
    }

}
