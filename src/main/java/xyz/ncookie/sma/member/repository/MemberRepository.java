package xyz.ncookie.sma.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.ncookie.sma.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
