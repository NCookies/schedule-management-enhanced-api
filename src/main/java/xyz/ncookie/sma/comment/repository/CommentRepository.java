package xyz.ncookie.sma.comment.repository;

import xyz.ncookie.sma.comment.entity.Comment;
import xyz.ncookie.sma.global.repository.BaseRepository;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment, Long> {

    List<Comment> findBySchedule_id(Long scheduleId);

}
