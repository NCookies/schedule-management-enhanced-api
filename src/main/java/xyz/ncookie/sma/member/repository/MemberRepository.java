package xyz.ncookie.sma.member.repository;

import xyz.ncookie.sma.global.repository.BaseRepository;
import xyz.ncookie.sma.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends BaseRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

}
