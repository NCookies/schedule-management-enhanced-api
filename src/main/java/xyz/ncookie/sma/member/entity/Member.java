package xyz.ncookie.sma.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.ncookie.sma.global.entity.BaseEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 유저명

    @Column(unique = true)
    private String email;       // 이메일

    public static Member of(String name, String email) {
        return new Member(
                null,
                name,
                email
        );
    }

    public void updateMemberInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
