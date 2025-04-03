package xyz.ncookie.sma.schedule.dto.response;

import org.springframework.data.domain.Page;
import xyz.ncookie.sma.comment.dto.response.CommentResponseDto;
import xyz.ncookie.sma.comment.entity.Comment;
import xyz.ncookie.sma.member.dto.response.MemberSummaryDto;
import xyz.ncookie.sma.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record ScheduleWithCommentsResponseDto(
        Long id,
        String title,
        String contents,

        MemberSummaryDto memberSummaryDto,
        Page<CommentResponseDto> comments,

        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static ScheduleWithCommentsResponseDto fromEntity(Schedule findSchedule, Page<Comment> pageComments) {
        return new ScheduleWithCommentsResponseDto(
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContents(),

                MemberSummaryDto.fromEntity(findSchedule.getMember()),
                pageComments.map(CommentResponseDto::fromEntity),

                findSchedule.getCreatedAt(),
                findSchedule.getModifiedAt()
        );
    }

}
