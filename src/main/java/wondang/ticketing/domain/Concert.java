package wondang.ticketing.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;

@Entity @Table(name = "concert")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Concert {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id")
    private Long id;

    private String name;

    private int cntSeat = 0;

    private LocalDate startDate;

    private LocalTime startTime;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    @Builder
    public Concert(String name, LocalDateTime startDateTime) {
        this.name = name;
        this.startDate = startDateTime.toLocalDate();
        this.startTime = startDateTime.toLocalTime().truncatedTo(ChronoUnit.MILLIS));
    }

    public void addSeat(int cnt, int price) {
        for (int i = 0; i < cnt; i++) {
            this.seats.add(
                    Seat.builder()
                            .concert(this)
                            .number(cntSeat + i + 1)
                            .price(price)
                            .build()
            );
        }

        this.cntSeat += cnt;
    }

}
