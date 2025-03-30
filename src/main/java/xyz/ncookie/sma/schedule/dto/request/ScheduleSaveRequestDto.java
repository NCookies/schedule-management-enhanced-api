package xyz.ncookie.sma.schedule.dto.request;

public record ScheduleSaveRequestDto(
        Long memberId,
        String title,
        String contents
) {
}
