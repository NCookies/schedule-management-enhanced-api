package xyz.ncookie.sma.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.sma.comment.dto.request.CommentSaveRequestDto;
import xyz.ncookie.sma.comment.dto.request.CommentUpdateRequestDto;
import xyz.ncookie.sma.comment.dto.response.CommentResponseDto;
import xyz.ncookie.sma.comment.entity.Comment;
import xyz.ncookie.sma.comment.repository.CommentRepository;
import xyz.ncookie.sma.common.exception.BusinessException;
import xyz.ncookie.sma.common.exception.ErrorCode;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.repository.MemberRepository;
import xyz.ncookie.sma.schedule.entity.Schedule;
import xyz.ncookie.sma.schedule.repository.ScheduleRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 댓글 생성
     * @param scheduleId 일정 ID
     * @param memberId 회원 ID
     * @param dto 댓글 내용
     * @return 생성된 댓글 정보
     */
    @Transactional
    public CommentResponseDto saveComment(Long scheduleId, Long memberId, CommentSaveRequestDto dto) {

        Member findMember = memberRepository.findByIdOrElseThrow(memberId);
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment savedComment = commentRepository.save(
                Comment.of(dto.contents(), findMember, findSchedule)
        );

        return CommentResponseDto.fromEntity(savedComment);
    }

    /**
     * 댓글 단일 조회
     * @param commentId 댓글 ID
     * @return 댓글 정보
     */
    @Transactional(readOnly = true)
    public CommentResponseDto findCommentById(Long commentId) {

        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);
        return CommentResponseDto.fromEntity(findComment);
    }

    /**
     * 특정 일정에 달린 댓글 리스트 조회
     * 일정 단일 조회 시 댓글을 새로고침하거나 "더 보기" 등을 통해 추가적인 댓글을 조회할 때 사용할 수 있다.
     * @param pageable 페이징 시 사용되는 객체
     * @param scheduleId 일정 ID
     * @return 댓글 페이징 객체
     */
    @Transactional(readOnly = true)
    public Page<CommentResponseDto> findAllCommentsByScheduleId(Pageable pageable, Long scheduleId) {

        Page<Comment> commentPage = commentRepository.findBySchedule_id(pageable, scheduleId);

        return commentPage.map(CommentResponseDto::fromEntity);
    }

    /**
     * 댓글 수정
     * 댓글을 등록한 본인인지 검사하고 수정 작업을 수행한다.
     * @param commentId 댓글 ID
     * @param memberId 회원 ID
     * @param scheduleId 일정 ID
     * @param dto 수정할 댓글 내용
     * @return 수정된 댓글 정보
     */
    @Transactional
    public CommentResponseDto updateComment(Long commentId, Long memberId, Long scheduleId, CommentUpdateRequestDto dto) {

        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        verifyCommentOwner(findComment, memberId, scheduleId);

        findComment.updateComment(dto.contents());

        return CommentResponseDto.fromEntity(findComment);
    }

    /**
     * 댓글 삭제
     * 댓글을 등록한 본인인지 검사하고 삭제 작업을 수행한다.
     * @param commentId 댓글 ID
     * @param memberId 회원 ID
     * @param scheduleId 일정 ID
     */
    @Transactional
    public void deleteComment(Long commentId, Long memberId, Long scheduleId) {

        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        verifyCommentOwner(findComment, memberId, scheduleId);

        commentRepository.delete(findComment);
    }

    /**
     * 댓글 수정 또는 삭제 시 해당 일정을 작성한 회원인지 검증
     * @param findComment 수정 또는 삭제하려는 댓글 entity
     * @param memberId 회원 ID
     * @param scheduleId 일정 ID
     */
    public void verifyCommentOwner(Comment findComment, Long memberId, Long scheduleId) {

        Long findScheduleId = findComment.getSchedule().getId();
        if (!Objects.equals(scheduleId, findScheduleId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "일정 ID : " + scheduleId);
        }

        Long findMemberId = findComment.getMember().getId();
        if (!Objects.equals(memberId, findMemberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_OPERATION, "본인이 작성한 댓글만 수정 또는 삭제할 수 있습니다. 일정 ID, 회원 ID: " + findComment.getId() + ", " + findMemberId);
        }
    }

}
