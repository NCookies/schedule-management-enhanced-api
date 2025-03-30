package xyz.ncookie.sma.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ncookie.sma.member.dto.request.MemberCreateRequestDto;
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

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateSchedule(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequestDto dto
    ) {

        // 영속성 컨텍스트가 dirty checking 하므로 따로 save()를 호출하지 않아도 됨
        MemberResponseDto updatedSchedule = memberService.updateMember(memberId, dto);

        return ResponseEntity.ok().body(updatedSchedule);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long memberId) {

        memberService.deleteMember(memberId);

        return ResponseEntity.ok().body(null);
    }
}
