package wondang.ticketing.domain;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConcertTest {

    @Autowired private EntityManager em;

    @Test
    public void 공연_생성() {
        Concert concert = newConcert();

        em.persist(concert);
        em.flush();
        em.clear();

        Concert findConcert = em.find(Concert.class, concert.getId());

        // 공연 생성 직후에는 좌석이 없어야 한다.
        assertThat(findConcert.getCntSeat()).isEqualTo(0);
        assertThat(findConcert.getSeats()).isEmpty();
    }

    @Test
    public void 좌석_추가_cascade_전이() {
        int expensiveSeatCnt = 3;
        int expensiveSeatPrice = 15000;
        int cheapSeatCnt = 7;
        int cheapSeatPrice = 3000;

        Concert concert = newConcert();
        concert.addSeat(expensiveSeatCnt, expensiveSeatPrice);
        concert.addSeat(cheapSeatCnt, cheapSeatPrice);

        // 좌석 수는 추가한 좌석의 수와 같아야 한다.
        assertThat(concert.getCntSeat()).isEqualTo(expensiveSeatCnt + cheapSeatCnt);
        assertThat(concert.getCntSeat()).isEqualTo(concert.getSeats().size());

        em.persist(concert);
        em.flush();
        em.clear();

        Concert findConcert = em.find(Concert.class, concert.getId());
        List<Seat> findSeats = findConcert.getSeats();

        // cascade 전이에 의해 Seat 배열 또한 DB에 저장되어야 한다.
        List<Seat> savedSeats = em.createQuery("SELECT s from Seat s " +
                        " WHERE s.concert = :concert", Seat.class)
                .setParameter("concert", findConcert)
                .getResultList();

        // 저장되었던 좌석의 주소 배열을 영속상태인 Concert의 seats에서 가지고 있다
        for (int i = 0; i < expensiveSeatCnt + cheapSeatCnt; i++) {
            assertThat(savedSeats).isEqualTo(findSeats);
        }
    }

    @Test
    public void findSeatByNumber() {
        int cnt = 1;
        Concert concert = newConcert();
        concert.addSeat(cnt, 3000);

        em.persist(concert);
        Seat findSeat = concert.findSeatByNumber(cnt);

        assertThat(findSeat).isEqualTo(concert.getSeats().get(cnt - 1));
    }

    public static Concert newConcert() {
        return Concert.builder()
                .name("레디스를 써보자")
                .startDateTime(now())
                .build();
    }
}