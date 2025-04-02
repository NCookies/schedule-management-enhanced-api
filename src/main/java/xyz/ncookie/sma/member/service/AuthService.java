package xyz.ncookie.sma.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.ncookie.sma.member.dto.request.LoginRequestDto;
import xyz.ncookie.sma.global.exception.BusinessException;
import xyz.ncookie.sma.global.exception.ErrorCode;
import xyz.ncookie.sma.global.util.PasswordEncoder;
import xyz.ncookie.sma.member.dto.response.MemberResponseDto;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto login(LoginRequestDto dto) {

        Member findMember = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL, ": " + dto.email()));

        if (!passwordEncoder.matches(dto.password(), findMember.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return MemberResponseDto.fromEntity(findMember);
    }

}
