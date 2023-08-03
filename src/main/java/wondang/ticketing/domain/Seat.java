package wondang.ticketing.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity @Table(name = "seat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Seat {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert; // 공연

    private int number; // 좌석 위치번호

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "seat")
    private Ticket ticket; // 티켓

    private int price; // 금액

    @Enumerated(EnumType.STRING)
    private SeatStatus status; // EMPTY, OCCUPIED

    @Builder
    public static Seat Seat(Concert concert, int number, int price) {
        Seat seat = new Seat();

        seat.setConcert(concert);
        seat.setNumber(number);
        seat.setPrice(price);
        seat.setStatus(SeatStatus.EMPTY);

        return seat;
    }

}
