package xyz.ncookie.sma.schedule.dto.response;

import xyz.ncookie.sma.member.dto.response.MemberSummaryDto;
import xyz.ncookie.sma.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record ScheduleResponseDto(
        Long id,
        MemberSummaryDto memberSummaryDto,
        String title,
        String contents,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static ScheduleResponseDto fromEntity(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                MemberSummaryDto.fromEntity(schedule.getMember()),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

}
