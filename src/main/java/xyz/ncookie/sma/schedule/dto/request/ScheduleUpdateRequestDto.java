package xyz.ncookie.sma.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ScheduleUpdateRequestDto(
        @NotBlank @Size(max = 20) String title,
        @NotBlank @Size(max = 255) String contents
) {
}
