package xyz.ncookie.sma.comment.dto.response;

import xyz.ncookie.sma.comment.entity.Comment;
import xyz.ncookie.sma.member.dto.response.MemberSummaryDto;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        String contents,
        MemberSummaryDto memberSummaryDto,
        Long scheduleId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContents(),
                MemberSummaryDto.fromEntity(comment.getMember()),
                comment.getSchedule().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

}
