package xyz.ncookie.sma.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import xyz.ncookie.sma.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 유저명

    @Column(unique = true)
    private String email;       // 이메일

}
