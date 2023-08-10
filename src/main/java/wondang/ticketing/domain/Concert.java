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
        this.startTime = startDateTime.toLocalTime().truncatedTo(ChronoUnit.MILLIS);
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

    public Seat findSeatByNumber(int number) {
        if (number > cntSeat) {
            throw new IllegalStateException("해당 좌석은 존재하지 않습니다.");
        }

        return this.seats.get(number - 1);
    }

    public Boolean isOver() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime time = now.toLocalTime();

        if (this.startDate.isEqual(date)) {
//            return this.startTime.isBefore(time) || this.startTime.isAfter(time);
            return !this.startTime.isAfter(time);
        }
        return this.startDate.isBefore(now());
    }
}
