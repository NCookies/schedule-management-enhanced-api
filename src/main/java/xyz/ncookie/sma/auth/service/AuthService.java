package xyz.ncookie.sma.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.ncookie.sma.auth.dto.request.LoginRequestDto;
import xyz.ncookie.sma.global.exception.BusinessException;
import xyz.ncookie.sma.global.exception.ErrorCode;
import xyz.ncookie.sma.member.dto.response.MemberResponseDto;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public MemberResponseDto login(LoginRequestDto dto) {

        Member findMember = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL, ": " + dto.email()));

        // TODO: PasswordEncoder 사용 시 matches 로 변경
        if (!findMember.getPassword().equals(dto.password())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return MemberResponseDto.fromEntity(findMember);
    }

}
