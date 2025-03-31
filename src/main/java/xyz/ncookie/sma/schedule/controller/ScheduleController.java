package xyz.ncookie.sma.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ncookie.sma.schedule.dto.request.ScheduleSaveRequestDto;
import xyz.ncookie.sma.schedule.dto.request.ScheduleUpdateRequestDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleResponseDto;
import xyz.ncookie.sma.schedule.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleSaveRequestDto dto) {

        ScheduleResponseDto savedSchedule = scheduleService.saveSchedule(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {

        ScheduleResponseDto findSchedule = scheduleService.getScheduleById(scheduleId);

        return ResponseEntity.ok().body(findSchedule);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleUpdateRequestDto dto
    ) {

        ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(scheduleId, dto);

        return ResponseEntity.ok().body(updatedSchedule);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {

        scheduleService.deleteScheduleById(scheduleId);

        return ResponseEntity.ok().body(null);
    }

}
