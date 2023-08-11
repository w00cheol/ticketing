package wondang.ticketing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wondang.ticketing.domain.*;
import wondang.ticketing.policy.DiscountPolicy;
import wondang.ticketing.repository.TicketRepository;

@Transactional
@Service
public class TicketService {

    @Autowired TicketRepository ticketRepository;
    @Autowired MemberService memberService;
    @Autowired ConcertService concertService;
    @Autowired DiscountPolicy discountPolicy;

    public Long createTicket(Long memberId, Long concertId, int number) {
        Member member = memberService.findById(memberId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 회원입니다.")
        );

        Concert concert = concertService.findById(concertId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 공연입니다.")
        );

        Seat seat = concert.findSeatByNumber(number);

        int totalPrice = discountPolicy.discount(member);

        Ticket newTicket = Ticket.builder()
                .member(member)
                .seat(seat)
                .totalPrice(totalPrice)
                .build();

        Ticket savedTicket = ticketRepository.save(newTicket);
        return savedTicket.getId();
    }
}
