package wondang.ticketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wondang.ticketing.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
