package xyz.ncookie.sma.auth.dto.request;

public record LoginRequestDto(
        String email,
        String password
) {
}
