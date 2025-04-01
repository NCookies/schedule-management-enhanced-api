package xyz.ncookie.sma.schedule.dto.response;

import xyz.ncookie.sma.member.dto.response.MemberSummaryDto;

import java.time.LocalDateTime;

// Page 객체에 담아 응답에 보낼 DTO
public record ScheduleWithCommentCountResponseDto(
        Long id,                        // 일정 ID
        String title,                   // 제목
        String contents,                // 내용

        MemberSummaryDto memberSummary, // 회원 정보

        Long commentCount,              // 댓글 개수

        LocalDateTime createdAt,        // 작성일
        LocalDateTime modifiedAt        // 수정일
) {
}
