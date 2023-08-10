package wondang.ticketing.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wondang.ticketing.domain.Concert;
import wondang.ticketing.domain.ConcertTest;
import wondang.ticketing.domain.Seat;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConcertServiceImplTest {

    @Autowired ConcertService concertService;
    @Autowired EntityManager em;

    public Concert dummyConcert() {
        return Concert.builder()
                .name("dummy name")
                .startDateTime(LocalDateTime.now())
                .build();
    }

    @Test
    public void 공연_생성() {
        Concert concert = ConcertTest.newConcert();

        Long savedId = concertService.createConcert(
                concert.getName(),
                concert.getStartTime().atDate(concert.getStartDate())
        );

        Concert findConcert = concertService.findById(savedId).orElseThrow();
        assertThat(findConcert.getName()).isEqualTo(concert.getName());
        assertThat(findConcert.getStartDate()).isEqualTo(concert.getStartDate());
    }

    @Test
    public void 좌석_추가() {
        int cntA = 5;
        int priceA = 7000;
        int cntB = 2;
        int priceB = 11000;

        Concert concert = ConcertTest.newConcert();

        Long savedId = concertService.createConcert(
                concert.getName(),
                concert.getStartTime().atDate(concert.getStartDate())
        );
        Concert findConcert = concertService.findById(savedId).orElseThrow();

        concertService.addSeat(savedId, cntA, priceA);
        concertService.addSeat(savedId, cntB, priceB);

        List<Seat> findSeats = em.createQuery("SELECT s FROM Seat s WHERE s.concert = :concert", Seat.class)
                .setParameter("concert", findConcert)
                .getResultList();

        int sum = findSeats.stream().mapToInt(Seat::getPrice).sum();

        Assertions.assertThat(findConcert.getCntSeat()).isEqualTo(cntA + cntB);
        Assertions.assertThat(findSeats.size()).isEqualTo(cntA + cntB);
        Assertions.assertThat(sum).isEqualTo((cntA * priceA) + (cntB * priceB));
    }
}