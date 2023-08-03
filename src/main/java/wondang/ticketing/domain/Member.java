package wondang.ticketing.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // 엔티티 조건에는 @Valid혹은 영속 시 예외 발생하는 @NotNull (jakarta) 보다
    // 그 전에 엔티티 생성 시 예외 발생할 수 있는 @NonNull (lombok) 사용
    @NonNull()
    @Column(unique = true)
    private String username;

    @NonNull()
    @Column(unique = true)
    private String nickname;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @Builder
    public Member(String username, String nickname) {
        this.setUsername(username);
        this.setNickname(nickname);
    }

}
