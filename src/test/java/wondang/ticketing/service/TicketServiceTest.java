package wondang.ticketing.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wondang.ticketing.domain.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketServiceTest {

    @Autowired MemberService memberService;
    @Autowired ConcertService concertService;
    @Autowired TicketService ticketService;

    @Test
    public void createTicket() {
        int cnt = 1;

        Member member = MemberTest.newMember("uname", "nname");
        Long memberId = memberService.signUp(member);
        Member findMember = memberService.findById(memberId).orElseThrow();

        Concert newConcert = ConcertTest.newConcert();
        Long concertId = concertService.createConcert(
                newConcert.getName(),
                newConcert.getStartTime().atDate(newConcert.getStartDate())
        );
        Concert findConcert = concertService.findById(concertId).orElseThrow();
        concertService.addSeat(concertId, cnt, 10000);

        assertThrows(IllegalStateException.class, () -> {
                    ticketService.createTicket(0L, concertId, cnt);
                }, "회원이 존재하지 않아 오류가 발생해야 한다."
        );

        assertThrows(IllegalStateException.class, () -> {
                    ticketService.createTicket(memberId, 0L, cnt);
                }, "공연이 존재하지 않아 오류가 발생해야 한다."
        );

        assertThrows(IllegalStateException.class, () -> {
                    ticketService.createTicket(memberId, concertId, cnt + 1);
                }, "공연이 존재하지 않아 오류가 발생해야 한다."
        );

        // 좌석 생성 시 기본값인 EMPTY 가 되어있어야 한다.
        Seat findSeat = findConcert.getSeats().get(cnt - 1);
        assertThat(findSeat.getStatus()).isEqualTo(SeatStatus.EMPTY);

        Long ticketId = ticketService.createTicket(memberId, concertId, cnt);
        Ticket findTicket = ticketService.findById(ticketId).orElseThrow();

        // 티켓 생성 시 좌석 상태는 OCCUPIED 되어야 한다.
        assertThat(findSeat.getStatus()).isEqualTo(SeatStatus.OCCUPIED);

        // 티켓이 Member 엔티티에도 동일하게 저장되어야 한다.
        assertThat(findMember.getTickets().get(cnt - 1)).isEqualTo(findTicket);

        // 티켓이 Seat 엔티티에도 동일하게 저장되어야 한다.
        assertThat(findSeat.getTicket()).isEqualTo(findTicket);
    }
}