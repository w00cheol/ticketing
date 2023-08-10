package wondang.ticketing.service;

import wondang.ticketing.domain.Member;

import java.util.Optional;

public interface MemberService {

    // todo: 데이터가 많아지면 DTO로 변환
    Long signUp(Member member);

    Optional<Member> findByUsername(String username);
}
