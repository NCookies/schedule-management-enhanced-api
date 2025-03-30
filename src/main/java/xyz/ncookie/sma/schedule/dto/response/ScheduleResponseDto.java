package xyz.ncookie.sma.schedule.dto.response;

import xyz.ncookie.sma.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record ScheduleResponseDto(
        Long id,
        String memberName,
        String title,
        String contents,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static ScheduleResponseDto fromEntity(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getMemberName(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

}
