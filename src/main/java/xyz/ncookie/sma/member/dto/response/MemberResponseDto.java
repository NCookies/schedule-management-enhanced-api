package xyz.ncookie.sma.member.dto.response;

import xyz.ncookie.sma.member.entity.Member;

import java.time.LocalDateTime;

public record MemberResponseDto(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

}
