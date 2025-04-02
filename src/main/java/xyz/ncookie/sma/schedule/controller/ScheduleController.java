package xyz.ncookie.sma.schedule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ncookie.sma.common.data.SessionConst;
import xyz.ncookie.sma.member.dto.response.MemberResponseDto;
import xyz.ncookie.sma.schedule.dto.request.ScheduleSaveRequestDto;
import xyz.ncookie.sma.schedule.dto.request.ScheduleUpdateRequestDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleResponseDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleWithCommentCountResponseDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleWithCommentsResponseDto;
import xyz.ncookie.sma.schedule.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto memberDto,
            @Valid @RequestBody ScheduleSaveRequestDto dto
    ) {

        ScheduleResponseDto savedSchedule = scheduleService.saveSchedule(memberDto.id(), dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleWithCommentsResponseDto> getSchedule(@PathVariable Long scheduleId) {

        ScheduleWithCommentsResponseDto findSchedule = scheduleService.findScheduleWithComments(scheduleId);

        return ResponseEntity.ok().body(findSchedule);
    }

    @GetMapping
    public ResponseEntity<Page<ScheduleWithCommentCountResponseDto>> getAllSchedules(
            @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<ScheduleWithCommentCountResponseDto> findAllSchedules = scheduleService.findAllSchedules(pageable);

        return ResponseEntity.ok().body(findAllSchedules);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto memberDto,
            @Valid @RequestBody ScheduleUpdateRequestDto dto
    ) {

        ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(scheduleId, memberDto.id(), dto);

        return ResponseEntity.ok().body(updatedSchedule);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto memberDto
    ) {

        scheduleService.deleteScheduleById(scheduleId, memberDto.id());

        return ResponseEntity.ok().body(null);
    }

}
