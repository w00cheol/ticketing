package wondang.ticketing.domain;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberTest {

    @Autowired private EntityManager em;

    public static Member newMember(String username, String nickname) {
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .build();
    }

    @Test
    public void 멤버_생성() {
        Member member = newMember("w00cheol", "우떠리");

        em.persist(member);
        em.flush();
        em.clear();
        Member findMember = em.find(Member.class, member.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getClass()).isEqualTo(member.getClass());
        assertThat(findMember.getNickname()).isEqualTo(member.getNickname());
        System.out.println("member.getNickname() = " + member.getNickname());
    }
}