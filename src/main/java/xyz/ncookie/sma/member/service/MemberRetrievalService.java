package xyz.ncookie.sma.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.ncookie.sma.global.exception.BusinessException;
import xyz.ncookie.sma.global.exception.ErrorCode;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.repository.MemberRepository;

/**
 * 서비스 클래스들의 순환 참조를 방지하기 위해 Member 검색용 클래스 분리
 */
@Service
@RequiredArgsConstructor
public class MemberRetrievalService {

    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, " 존재하지 않는 ID입니다. : " + memberId));
    }

}
