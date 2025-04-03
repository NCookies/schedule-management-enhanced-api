package xyz.ncookie.sma.member.dto.request;

import xyz.ncookie.sma.common.validator.ValidEmail;
import xyz.ncookie.sma.common.validator.ValidPassword;

public record LoginRequestDto(
        @ValidEmail String email,
        @ValidPassword String password
) {
}
