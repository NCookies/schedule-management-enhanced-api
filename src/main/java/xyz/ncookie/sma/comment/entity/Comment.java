package xyz.ncookie.sma.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.ncookie.sma.global.entity.BaseEntity;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.schedule.entity.Schedule;

@Entity
@Table(name = "comment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;          // 작성 유저

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;      // 댓글이 위치한 일정

}
