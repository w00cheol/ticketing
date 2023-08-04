package wondang.ticketing.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "ticketing")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Ticket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat; // 좌석

    private int totalPrice; // 최종 결제 금액

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    public void cancel() {
        this.setStatus(TicketStatus.CANCEL);
    }

    public static Ticket createTicket(Member member, Seat seat, int totalPrice) {
        Ticket ticket = new Ticket();

        ticket.setMember(member);
        member.getTickets().add(ticket);

        ticket.setSeat(seat);
        ticket.setTotalPrice(totalPrice);
        ticket.setStatus(TicketStatus.PAID);

        return ticket;
    }
}
