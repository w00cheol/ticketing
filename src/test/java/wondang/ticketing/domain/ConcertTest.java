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
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConcertTest {

    @Autowired private EntityManager em;

    @Test
    public void 공연_생성() {
        int cntSeat = 5;
        int basePrice = 10000;
        Concert concert = Concert.builder()
                .name("레디스를 써보자")
                .cntSeat(cntSeat)
                .startDateTime(now())
                .price(basePrice)
                .build();

        em.persist(concert);
        em.flush();
        em.clear();
        Concert fincConcert = em.find(Concert.class, concert.getId());

        assertThat(fincConcert.getId()).isEqualTo(concert.getId());
        assertThat(fincConcert.getClass()).isEqualTo(concert.getClass());
        assertThat(fincConcert.getStartTime()).isEqualTo(concert.getStartTime());

        List<Seat> seats = concert.getSeats();
        List<Seat> findSeats = fincConcert.getSeats();
        for (int i = 0; i < cntSeat; i++) {
            assertThat(findSeats.get(i).getId()).isEqualTo(seats.get(i).getId());
        }
    }
}