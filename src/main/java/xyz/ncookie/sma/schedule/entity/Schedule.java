package xyz.ncookie.sma.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.ncookie.sma.global.entity.BaseEntity;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberName;      // 작성 유저

    @Column(nullable = false)
    private String title;       // 할 일 제목

    @Column(columnDefinition = "longtext")
    private String contents;    // 할 일 내용

    public static Schedule of(String memberName, String title, String contents) {
        return new Schedule(
                null,
                memberName,
                title,
                contents
        );
    }

    public void updateSchedule(String memberName, String title, String contents) {
        this.memberName = memberName;
        this.title = title;
        this.contents = contents;
    }

}
