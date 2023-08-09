package wondang.ticketing.domain;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketTest {

    @Autowired private EntityManager em;

    @Test
    public void 티켓_생성() {
        int extraPrice = 5000;
        Member member = em.find(Member.class, 1L);
        Concert concert = em.find(Concert.class, 1L);

        Optional<Seat> seat = em.createQuery("SELECT s FROM Seat s" +
                        " WHERE s.concert = :concert and s.status = :status"
                        , Seat.class)
                .setParameter("concert", concert)
                .setParameter("status", SeatStatus.EMPTY)
                .getResultList()
                .stream()
                .findAny();

        Seat findSeat = seat.orElseThrow(() -> new IllegalArgumentException());

        Ticket ticket = Ticket.builder()
                .member(member)
                .seat(findSeat)
                .totalPrice(findSeat.getPrice() + extraPrice)
                .build();

        System.out.println("ticket.getTotalPrice() = " + ticket.getTotalPrice());
        System.out.println("ticket.getSeat().getId() = " + ticket.getSeat().getId());
        em.persist(ticket);
        em.flush();
        em.clear();
        Ticket findTicket = em.find(Ticket.class, ticket.getId());

        assertThat(findTicket.getMember().getId()).isEqualTo(ticket.getMember().getId());
        assertThat(findTicket.getSeat().getId()).isEqualTo(ticket.getSeat().getId());
        assertThat(findTicket.getTotalPrice()).isEqualTo(ticket.getTotalPrice());
    }

}