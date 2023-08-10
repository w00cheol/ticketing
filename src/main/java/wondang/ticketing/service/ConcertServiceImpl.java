package wondang.ticketing.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wondang.ticketing.domain.Concert;
import wondang.ticketing.repository.ConcertRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
public class ConcertServiceImpl implements ConcertService {

    @Autowired ConcertRepository concertRepository;

    @Override
    public Long createConcert(String name, LocalDateTime startDateTime) {
        Concert newConcert = Concert.builder()
                .name(name)
                .startDateTime(startDateTime)
                .build();

        Concert savedConcert = concertRepository.save(newConcert);
        return savedConcert.getId();
    }

    @Override
    public Optional<Concert> findById(Long id) {
        return concertRepository.findById(id);
    }
}
