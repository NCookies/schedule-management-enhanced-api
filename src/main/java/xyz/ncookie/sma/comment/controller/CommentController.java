package xyz.ncookie.sma.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ncookie.sma.comment.dto.request.CommentSaveRequestDto;
import xyz.ncookie.sma.comment.dto.request.CommentUpdateRequestDto;
import xyz.ncookie.sma.comment.dto.response.CommentResponseDto;
import xyz.ncookie.sma.comment.service.CommentService;
import xyz.ncookie.sma.common.data.SessionConst;
import xyz.ncookie.sma.member.dto.response.MemberResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> saveComment(
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto memberDto,
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentSaveRequestDto dto
    ) {

        CommentResponseDto savedComment = commentService.saveComment(scheduleId, memberDto.id(), dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> findComment(@PathVariable Long commentId) {

        CommentResponseDto findComment = commentService.findCommentById(commentId);

        return ResponseEntity.ok().body(findComment);
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> findAllComments(
            @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long scheduleId
    ) {

        Page<CommentResponseDto> findAllComments = commentService.findAllCommentsByScheduleId(pageable, scheduleId);

        return ResponseEntity.ok().body(findAllComments);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto memberDto,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequestDto dto
    ) {

        CommentResponseDto updatedComment = commentService.updateComment(commentId, memberDto.id(), scheduleId, dto);

        return ResponseEntity.ok().body(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(
            @SessionAttribute(name = SessionConst.LOGIN_USER) MemberResponseDto memberDto,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {

        commentService.deleteComment(commentId, memberDto.id(), scheduleId);

        return ResponseEntity.ok().build();
    }
}
