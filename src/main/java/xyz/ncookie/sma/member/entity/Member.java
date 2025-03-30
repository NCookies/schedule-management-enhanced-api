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

    public void updateMemberInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
