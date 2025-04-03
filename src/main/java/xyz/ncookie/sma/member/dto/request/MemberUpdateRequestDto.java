package xyz.ncookie.sma.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberUpdateRequestDto(
        @NotBlank @Size(max = 8) String name
) {
}
