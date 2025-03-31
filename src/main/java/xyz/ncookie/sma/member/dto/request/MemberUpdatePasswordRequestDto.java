package xyz.ncookie.sma.member.dto.request;

public record MemberUpdatePasswordRequestDto(
        String oldPassword,
        String newPassword
) {
}
