package xyz.ncookie.sma.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.sma.common.exception.BusinessException;
import xyz.ncookie.sma.common.exception.ErrorCode;
import xyz.ncookie.sma.common.util.PasswordEncoder;
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

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     * @param dto 회원 가입 시 필요한 정보를 포함하는 DTO
     * @return 등록된 회원 정보
     */
    @Transactional
    public MemberResponseDto createMember(MemberCreateRequestDto dto) {

        // email 중복 예외 처리
        if (existsByEmail(dto.email())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS, ": " + dto.email());
        }

        String encodedPassword = passwordEncoder.encode(dto.password());

        Member createdMember = memberRepository.save(
                Member.of(dto.name(), dto.email(), encodedPassword)
        );

        return MemberResponseDto.fromEntity(createdMember);
    }

    /**
     * 회원 단일 조회
     * @param memberId 회원 ID
     * @return 회원 정보
     */
    @Transactional(readOnly = true)
    public MemberResponseDto getMemberById(Long memberId) {

        return MemberResponseDto.fromEntity(
                memberRepository.findByIdOrElseThrow(memberId)
        );
    }

    /**
     * 회원 정보 수정
     * @param memberId 회원 ID
     * @param dto 회원 정보 수정할 내용을 포함한 DTO
     * @return 수정된 회원 정보
     */
    @Transactional
    public MemberResponseDto updateMemberInfo(Long memberId, MemberUpdateRequestDto dto) {

        Member findMember = memberRepository.findByIdOrElseThrow(memberId);

        findMember.updateMemberInfo(dto.name());

        return MemberResponseDto.fromEntity(findMember);
    }

    /**
     * 회원 비밀번호 수정. 기존 비밀번호와 일치하는지 검증 후 수정하도록 한다.
     * @param memberId 회원 ID
     * @param dto 기존 비밀번호, 변경할 비밀번호 포함하는 DTO
     * @return 수정된 회원 정보 반환
     */
    @Transactional
    public MemberResponseDto updateMemberPassword(Long memberId, MemberUpdatePasswordRequestDto dto) {

        Member findMember = memberRepository.findByIdOrElseThrow(memberId);
        
        if (!passwordEncoder.matches(dto.oldPassword(), findMember.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD, " 회원 ID: " + memberId);
        }

        String encodedPassword = passwordEncoder.encode(dto.newPassword());
        findMember.updatePassword(encodedPassword);

        return MemberResponseDto.fromEntity(findMember);
    }

    /**
     * 회원 탈퇴
     * @param memberId 회원 ID
     */
    @Transactional
    public void deleteMember(Long memberId) {

        Member findMember = memberRepository.findByIdOrElseThrow(memberId);
        memberRepository.delete(findMember);
    }

    /**
     * 가입하려는 회원의 이메일이 기존의 것과 충돌하는 것이 있는지 검사
     * @param email 가입할 때 사용하려는 이메일
     * @return 이미 존재하는 이메일이라면 true, 아니면 false
     */
    private boolean existsByEmail(String email) {

        return memberRepository.findByEmail(email).isPresent();
    }

}
