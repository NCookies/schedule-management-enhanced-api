package xyz.ncookie.sma.schedule.dto.request;

public record ScheduleSaveRequestDto(
        String memberName,
        String title,
        String contents
) {
}
