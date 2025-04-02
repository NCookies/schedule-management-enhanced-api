package xyz.ncookie.sma.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.ncookie.sma.comment.entity.Comment;
import xyz.ncookie.sma.global.repository.BaseRepository;

public interface CommentRepository extends BaseRepository<Comment, Long> {

    Page<Comment> findBySchedule_id(Pageable pageable, Long scheduleId);

}
