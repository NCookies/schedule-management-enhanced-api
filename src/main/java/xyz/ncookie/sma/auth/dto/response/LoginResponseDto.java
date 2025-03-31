package xyz.ncookie.sma.auth.dto.response;

import xyz.ncookie.sma.member.entity.Member;

public record LoginResponseDto(
        Long id,
        String name,
        String email
) {

    public static LoginResponseDto fromEntity(Member member) {
        return new LoginResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }

}
