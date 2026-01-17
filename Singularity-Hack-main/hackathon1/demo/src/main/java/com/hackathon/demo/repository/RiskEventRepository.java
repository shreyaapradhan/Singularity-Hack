package com.hackathon.demo.repository;

import com.hackathon.demo.model.RiskEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RiskEventRepository extends JpaRepository<RiskEvent, Long> {

    Optional<RiskEvent> findByUserIdAndStatus(Long userId, String status);

    List<RiskEvent> findByStatusAndCheckInDeadlineBefore(
            String status,
            LocalDateTime time
    );
}
