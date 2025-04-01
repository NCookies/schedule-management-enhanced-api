package xyz.ncookie.sma.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import xyz.ncookie.sma.global.validator.ValidPassword;

public record MemberUpdatePasswordRequestDto(
        @NotBlank String oldPassword,
        @ValidPassword String newPassword
) {
}
