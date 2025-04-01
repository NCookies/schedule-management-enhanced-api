package xyz.ncookie.sma.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import xyz.ncookie.sma.global.validator.ValidEmail;
import xyz.ncookie.sma.global.validator.ValidPassword;

public record MemberCreateRequestDto(
        @NotBlank @Size(max = 8) String name,
        @ValidEmail String email,
        @ValidPassword String password
) {
}
