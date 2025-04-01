package xyz.ncookie.sma.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.sma.global.exception.BusinessException;
import xyz.ncookie.sma.global.exception.ErrorCode;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.service.MemberRetrievalService;
import xyz.ncookie.sma.schedule.dto.request.ScheduleSaveRequestDto;
import xyz.ncookie.sma.schedule.dto.request.ScheduleUpdateRequestDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleResponseDto;
import xyz.ncookie.sma.schedule.entity.Schedule;
import xyz.ncookie.sma.schedule.repository.ScheduleRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final MemberRetrievalService memberRetrievalService;

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto saveSchedule(Long memberId, ScheduleSaveRequestDto dto) {

        Member member = memberRetrievalService.findById(memberId);

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
    public ScheduleResponseDto updateSchedule(Long scheduleId, Long memberId, ScheduleUpdateRequestDto dto) {

        Schedule findSchedule = findById(scheduleId);

        verifyScheduleOwner(findSchedule, memberId);

        findSchedule.updateSchedule(dto.title(), dto.contents());

        // 영속성 컨텍스트가 dirty checking 하므로 따로 save()를 호출하지 않아도 됨
        return ScheduleResponseDto.fromEntity(findSchedule);
    }

    @Transactional
    public void deleteScheduleById(Long scheduleId, Long memberId) {

        Schedule findSchedule = findById(scheduleId);

        verifyScheduleOwner(findSchedule, memberId);

        scheduleRepository.delete(findSchedule);
    }

    private Schedule findById(Long scheduleId) {

        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, " 존재하지 않는 ID입니다. : " + scheduleId));
    }

    // 일정 수정 또는 삭제 시 해당 일정을 작성한 회원인지 검증
    private void verifyScheduleOwner(Schedule findSchedule, Long sessionMemberId) {

        Long findMemberId = findSchedule.getMember().getId();
        if (!Objects.equals(sessionMemberId, findMemberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_OPERATION, "본인이 작성한 일정만 수정 또는 삭제할 수 있습니다. 일정 ID, 회원 ID: " + findSchedule.getId() + ", " + findMemberId);
        }
    }

}
