package xyz.ncookie.sma.schedule.dto.request;

public record ScheduleUpdateRequestDto(
        String memberName,
        String title,
        String contents
) {
}
