package wondang.ticketing.service;

import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wondang.ticketing.domain.Concert;
import wondang.ticketing.domain.ConcertTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConcertServiceImplTest {

    @Autowired ConcertService concertService;

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
}