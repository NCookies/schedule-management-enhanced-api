package xyz.ncookie.sma.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ncookie.sma.global.data.SessionConst;
import xyz.ncookie.sma.member.dto.request.MemberCreateRequestDto;
import xyz.ncookie.sma.member.dto.request.MemberUpdatePasswordRequestDto;
import xyz.ncookie.sma.member.dto.request.MemberUpdateRequestDto;
import xyz.ncookie.sma.member.dto.response.MemberResponseDto;
import xyz.ncookie.sma.member.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberResponseDto> register(@RequestBody MemberCreateRequestDto dto) {

        MemberResponseDto createdMember = memberService.createMember(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    // 로그인한 회원 자신의 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyInfo(
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto loginMember
    ) {

        return ResponseEntity.ok().body(loginMember);
    }

    // 다른 회원의 정보 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId) {

        MemberResponseDto findMember = memberService.getMemberById(memberId);

        return ResponseEntity.ok().body(findMember);
    }

    // 프로필 수정
    @PutMapping("/me/info")
    public ResponseEntity<MemberResponseDto> updateMemberInfo(
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto loginMember,
            @RequestBody MemberUpdateRequestDto dto
    ) {

        MemberResponseDto updatedMember = memberService.updateMemberInfo(loginMember.id(), dto);

        return ResponseEntity.ok().body(updatedMember);
    }

    // 비밀번호 수정
    @PutMapping("/me/password")
    public ResponseEntity<MemberResponseDto> updateMemberPassword(
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto loginMember,
            @RequestBody MemberUpdatePasswordRequestDto dto
    ) {

        MemberResponseDto updatedMember = memberService.updateMemberPassword(loginMember.id(), dto);

        return ResponseEntity.ok().body(updatedMember);
    }

    // 회원 삭제
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMember(
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto loginMember
    ) {

        memberService.deleteMember(loginMember.id());

        return ResponseEntity.ok().body(null);
    }
}
