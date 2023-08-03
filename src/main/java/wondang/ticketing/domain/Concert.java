package wondang.ticketing.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "concert")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Concert {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id")
    private Long id;

    private String name;

    private int cntSeat;

    private LocalDate startDate;

    private LocalTime startTime;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    @Builder
    public static Concert Concert(String name, int cntSeat, LocalDateTime startDateTime, int price) {
        Concert concert = new Concert();

        concert.setName(name);
        concert.setCntSeat(cntSeat);
        concert.setStartDate(startDateTime.toLocalDate());
        concert.setStartTime(startDateTime.toLocalTime().truncatedTo(ChronoUnit.MILLIS));

        for (int i = 0; i < cntSeat; i++) {
            concert.getSeats().add(Seat.builder()
                    .concert(concert)
                    .number(i + 1)
                    .price(price)
                    .build());
        }

        return concert;
    }

}
