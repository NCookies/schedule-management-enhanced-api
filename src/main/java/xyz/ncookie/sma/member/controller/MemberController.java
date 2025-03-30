package xyz.ncookie.sma.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<MemberResponseDto> saveSchedule(@RequestBody MemberCreateRequestDto dto) {

        MemberResponseDto createdMember = memberService.createMember(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getSchedule(@PathVariable Long memberId) {

        MemberResponseDto findMember = memberService.getMemberById(memberId);

        return ResponseEntity.ok().body(findMember);
    }

    @PutMapping("/{memberId}/info")
    public ResponseEntity<MemberResponseDto> updateSchedule(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequestDto dto
    ) {

        MemberResponseDto updatedMember = memberService.updateMemberInfo(memberId, dto);

        return ResponseEntity.ok().body(updatedMember);
    }

    @PutMapping("/{memberId}/password")
    public ResponseEntity<MemberResponseDto> updateSchedule(
            @PathVariable Long memberId,
            @RequestBody MemberUpdatePasswordRequestDto dto
    ) {

        MemberResponseDto updatedMember = memberService.updateMemberPassword(memberId, dto);

        return ResponseEntity.ok().body(updatedMember);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long memberId) {

        memberService.deleteMember(memberId);

        return ResponseEntity.ok().body(null);
    }
}
