package wondang.ticketing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wondang.ticketing.domain.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
}
