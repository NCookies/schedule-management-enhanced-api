package xyz.ncookie.sma.schedule.dto.request;

public record ScheduleUpdateRequestDto(
        Long memberId,
        String title,
        String contents
) {
}
