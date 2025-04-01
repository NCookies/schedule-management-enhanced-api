package xyz.ncookie.sma.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.ncookie.sma.global.entity.BaseEntity;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 유저명

    @Column(unique = true, updatable = false)
    private String email;       // 이메일

    @Column(nullable = false)
    private String password;

    public static Member of(String name, String email, String password) {
        return new Member(
                null,
                name,
                email,
                password
        );
    }

    public void updateMemberInfo(String name) {
        this.name = name;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
