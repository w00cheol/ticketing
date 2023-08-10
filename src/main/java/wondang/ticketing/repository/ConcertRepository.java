package wondang.ticketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wondang.ticketing.domain.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
