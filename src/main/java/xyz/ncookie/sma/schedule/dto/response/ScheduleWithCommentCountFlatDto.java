package xyz.ncookie.sma.schedule.dto.response;

import xyz.ncookie.sma.member.dto.response.MemberSummaryDto;

import java.time.LocalDateTime;

// JPA는 기본적으로 DTO에 매핑시켜줄 때 중첩된 객체는 지원하지 않는다.
// 때문에 먼저 평탄화된 DTO에 데이터를 매핑시키고, 사용 시 ScheduleWithCommentResponseDto로 변환한다.
public record ScheduleWithCommentCountFlatDto(
        Long id,                        // 일정 ID
        String title,                   // 제목
        String contents,                // 내용

        Long memberId,                  // 유저 ID
        String memberName,              // 유저 이름

        Long commentCount,              // 댓글 개수

        LocalDateTime createdAt,        // 작성일
        LocalDateTime modifiedAt        // 수정일
) {

    public ScheduleWithCommentCountResponseDto toResponseDto() {
        return new ScheduleWithCommentCountResponseDto(
                id,
                title,
                contents,
                MemberSummaryDto.of(memberId, memberName),
                commentCount,
                createdAt,
                modifiedAt
        );
    }

}
