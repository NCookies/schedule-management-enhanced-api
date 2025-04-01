package xyz.ncookie.sma.member.dto.response;

import xyz.ncookie.sma.member.entity.Member;

// Schedule 응답에 첨부될 DTO. id와 이름 정보만 가지고 있음
public record MemberSummaryDto(
        Long id,
        String name
) {

    public static MemberSummaryDto fromEntity(Member member) {
        return MemberSummaryDto.of(
                member.getId(),
                member.getName()
        );
    }

    public static MemberSummaryDto of(Long id, String name) {
        return new MemberSummaryDto(
                id,
                name
        );
    }

}
