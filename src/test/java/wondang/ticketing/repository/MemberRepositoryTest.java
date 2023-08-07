package wondang.ticketing.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wondang.ticketing.domain.Member;
import wondang.ticketing.domain.MemberTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager em;

    @Test
    public void 멤버_저장() {
        Member member = newMember1();
        // save
        Member savedMember = memberRepository.save(member);

        // member1은 entityManager에 의해 캐싱 상태
        assertThat(em.contains(member)).isTrue();
        // 반환된 savedMember1도 캐싱 상태
        assertThat(em.contains(savedMember)).isTrue();
        // 주소까지 같은 객체임
        assertThat(savedMember).isEqualTo(member);

        // 엔티티 find
        Member findMember = memberRepository.findById(member.getId()).orElseThrow();

        // 값 변경에 따라 영속 객체도 값 변경
        member.setUsername("member1 username");
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 값 변경에 따라 영속 객체도 값 변경
        savedMember.setUsername("savedMember1 username");
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
    }

    @Test
    public void 멤버_수정() {
        Member member1 = newMember1();
        Member member2 = newMember2();

        // save
        Member savedMember1 = memberRepository.save(member1);

        // save 시 id가 중복되므로 merge 되어야 함
        member2.setId(member1.getId());
        Member mergedMember = memberRepository.save(member2);

        // update 되어야 함.
        assertThat(mergedMember.getUsername()).isEqualTo(member2.getUsername());

        // parameter로 전달된 member2는 영속 상태 아님
        assertThat(em.contains(member2)).isFalse();
        // 반환된 mergedMember은 영속 상태
        assertThat(em.contains(mergedMember)).isTrue();
        // 따라서 주소값도 다름
        assertThat(mergedMember).isNotEqualTo(member2);

        // 관리되지 않은 객체는 영속객체에 영향을 주지 않음
        member2.setUsername("dummy username");
        assertThat(member2.getUsername()).isNotEqualTo(savedMember1.getUsername());

        // 반환된 mergedMember의 주소값이 영속 객체를 가리킴
        assertThat(mergedMember).isEqualTo(savedMember1);
    }

    @Test
    public void 멤버_조회() {
        Long memberId = 0L;
        Member member = newMember1();
        member.setId(memberId);

        // 영속 상태가 아니므로 검색되지 않아야 한다
        Optional<Member> findMemberById = memberRepository.findById(member.getId());
        assertThat(findMemberById.isEmpty()).isTrue();

        // persist
        Member savedMember = memberRepository.save(member);

        // 반환 객체만 영속성 컨텍스트에 의해 관리되어야 함
        assertThat(em.contains(member)).isFalse();
        assertThat(em.contains(savedMember)).isTrue();

        // genreate startegy에 의해 새로운 id를 부여받음
        assertThat(savedMember.getId()).isNotEqualTo(memberId);
    }

    private static Member newMember1() {
        return MemberTest.newMember(
                "member1 username",
                "member1 nickname"
        );
    }
    private static Member newMember2() {
        return MemberTest.newMember(
                "member2 username",
                "member2 nickname"
        );
    }
}