package xyz.ncookie.sma.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.sma.global.exception.BusinessException;
import xyz.ncookie.sma.global.exception.ErrorCode;
import xyz.ncookie.sma.member.dto.request.MemberCreateRequestDto;
import xyz.ncookie.sma.member.dto.request.MemberUpdatePasswordRequestDto;
import xyz.ncookie.sma.member.dto.request.MemberUpdateRequestDto;
import xyz.ncookie.sma.member.dto.response.MemberResponseDto;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRetrievalService memberRetrievalService;

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto createMember(MemberCreateRequestDto dto) {

        // email 중복 예외 처리
        if (existsByEmail(dto.email())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS, ": " + dto.email());
        }

        Member createdMember = memberRepository.save(
                Member.of(dto.name(), dto.email(), dto.password())
        );

        return MemberResponseDto.fromEntity(createdMember);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberById(Long memberId) {

        return MemberResponseDto.fromEntity(
                memberRetrievalService.findById(memberId)
        );
    }

    @Transactional
    public MemberResponseDto updateMemberInfo(Long memberId, MemberUpdateRequestDto dto) {

        Member findMember = memberRetrievalService.findById(memberId);

        findMember.updateMemberInfo(dto.name());

        return MemberResponseDto.fromEntity(findMember);
    }

    @Transactional
    public MemberResponseDto updateMemberPassword(Long memberId, MemberUpdatePasswordRequestDto dto) {

        Member findMember = memberRetrievalService.findById(memberId);
        
        // TODO: matches로 변경 예정
        if (!findMember.getPassword().equals(dto.oldPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD, " 회원 ID: " + memberId);
        }

        findMember.updatePassword(dto.newPassword());

        return MemberResponseDto.fromEntity(findMember);
    }

    @Transactional
    public void deleteMember(Long memberId) {

        Member findMember = memberRetrievalService.findById(memberId);
        memberRepository.delete(findMember);
    }

    private boolean existsByEmail(String email) {

        return memberRepository.findByEmail(email).isPresent();
    }

}
