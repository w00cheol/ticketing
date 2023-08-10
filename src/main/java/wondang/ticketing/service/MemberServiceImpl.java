package wondang.ticketing.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wondang.ticketing.domain.Concert;
import wondang.ticketing.domain.Member;
import wondang.ticketing.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired MemberRepository memberRepository;

    @Override
    public Long signUp(Member member) {

        validateDuplicateMember(member);

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembersByUsername = memberRepository.findAllByUsername(member.getUsername());
        if (!findMembersByUsername.isEmpty()) {
            throw new IllegalStateException("username 중복");
        }

        List<Member> findMembersByNickname = memberRepository.findAllByNickname(member.getNickname());
        if (!findMembersByNickname.isEmpty()) {
            throw new IllegalStateException("nickname 중복");
        }
    }
}