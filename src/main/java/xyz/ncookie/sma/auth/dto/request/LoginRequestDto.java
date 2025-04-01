package xyz.ncookie.sma.auth.dto.request;

import xyz.ncookie.sma.global.validator.ValidEmail;
import xyz.ncookie.sma.global.validator.ValidPassword;

public record LoginRequestDto(
        @ValidEmail String email,
        @ValidPassword String password
) {
}
