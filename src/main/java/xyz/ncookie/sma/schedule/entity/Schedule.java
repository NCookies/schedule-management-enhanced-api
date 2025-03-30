package xyz.ncookie.sma.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import xyz.ncookie.sma.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "schedule")
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

}
