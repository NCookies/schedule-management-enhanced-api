package xyz.ncookie.sma.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.service.MemberRetrievalService;
import xyz.ncookie.sma.schedule.dto.request.ScheduleSaveRequestDto;
import xyz.ncookie.sma.schedule.dto.request.ScheduleUpdateRequestDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleResponseDto;
import xyz.ncookie.sma.schedule.entity.Schedule;
import xyz.ncookie.sma.schedule.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final MemberRetrievalService memberRetrievalService;

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleSaveRequestDto dto) {

        Member member = memberRetrievalService.findById(dto.memberId());

        Schedule savedSchedule = scheduleRepository.save(
                Schedule.of(
                        member,
                        dto.title(),
                        dto.contents()
                )
        );

        return ScheduleResponseDto.fromEntity(savedSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getScheduleById(Long scheduleId) {

        return ScheduleResponseDto.fromEntity(findById(scheduleId));
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto dto) {

        Schedule findSchedule = findById(scheduleId);

        findSchedule.updateSchedule(dto.title(), dto.contents());

        return ScheduleResponseDto.fromEntity(findSchedule);
    }

    @Transactional
    public void deleteScheduleById(Long scheduleId) {

        Schedule findSchedule = findById(scheduleId);
        scheduleRepository.delete(findSchedule);
    }

    private Schedule findById(Long scheduleId) {

        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 ID입니다. = " + scheduleId));
    }

}
