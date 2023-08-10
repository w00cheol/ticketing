package wondang.ticketing.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wondang.ticketing.domain.Member;
import wondang.ticketing.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberServiceImplTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    private Member newMember() {
        return Member.builder()
                .username("username")
                .nickname("nickname")
                .build();
    }

    @Test
    public void 회원가입(){
        // given
        Member member = newMember();

        // when
        Long savedId = memberService.signUp(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findById(savedId).get());
    }
    
    @Test()
    public void 중복_예외(){
        // given
        Member member1 = newMember();
        Member member2 = newMember();

        // when
        Long savedId = memberService.signUp(member1);

        //then
        assertThrows(IllegalStateException.class, () -> {
            memberService.signUp(member2);
        }, "username 중복 발생");

        member2.setUsername("not duplicated username");
        assertThrows(IllegalStateException.class, () -> {
            memberService.signUp(member2);
        }, "nickname 중복 발생");

        member2.setNickname("not duplicated nickname");
        assertDoesNotThrow(() -> {
            memberService.signUp(member2);
        }, "정상 진행되어야 함");
    }
}