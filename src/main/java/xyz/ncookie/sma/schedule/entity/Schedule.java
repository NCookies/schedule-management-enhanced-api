package xyz.ncookie.sma.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.ncookie.sma.global.entity.BaseEntity;
import xyz.ncookie.sma.member.entity.Member;

@Entity
@Table(name = "schedule")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;      // 작성 유저

    @Column(nullable = false)
    private String title;       // 할 일 제목

    @Column(columnDefinition = "longtext")
    private String contents;    // 할 일 내용

    public static Schedule of(Member member, String title, String contents) {
        return new Schedule(
                null,
                member,
                title,
                contents
        );
    }

    public void updateSchedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
