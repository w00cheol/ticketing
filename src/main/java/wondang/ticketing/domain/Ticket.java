package wondang.ticketing.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "ticket")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Ticket(Member member, Seat seat, int totalPrice) {
        this.member = member;
        member.getTickets().add(this);

        this.seat = seat;
        seat.setStatus(SeatStatus.OCCUPIED);
        seat.setTicket(this);

        this.totalPrice = totalPrice;
        this.status = TicketStatus.PAID;
    }
}
