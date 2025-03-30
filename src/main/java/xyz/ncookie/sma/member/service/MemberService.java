package xyz.ncookie.sma.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.sma.global.exception.DuplicateEmailException;
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
            throw new DuplicateEmailException(dto.email());
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

        // 수정하려는 이메일이 이미 존재한다면 예외 발생
        // 같은 유저가 기존에 사용하던 이메일이라면 제외
        if (existsByEmail(dto.email()) && !dto.email().equals(findMember.getEmail())) {
            throw new DuplicateEmailException(dto.email());
        }

        findMember.updateMemberInfo(dto.name(), dto.email());

        return MemberResponseDto.fromEntity(findMember);
    }

    @Transactional
    public MemberResponseDto updateMemberPassword(Long memberId, MemberUpdatePasswordRequestDto dto) {

        Member findMember = memberRetrievalService.findById(memberId);

        findMember.updatePassword(dto.password());

        return MemberResponseDto.fromEntity(findMember);
    }

    @Transactional
    public void deleteMember(Long memberId) {

        Member findMember = memberRetrievalService.findById(memberId);
        memberRepository.delete(findMember);
    }

    private boolean existsByEmail(String email) {

        return !memberRepository.findByEmail(email).isEmpty();
    }

}
