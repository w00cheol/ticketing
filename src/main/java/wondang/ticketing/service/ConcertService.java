package wondang.ticketing.service;

import wondang.ticketing.domain.Concert;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConcertService {

    Long createConcert(String name, LocalDateTime localDateTime);

    Optional<Concert> findById(Long id);
}
