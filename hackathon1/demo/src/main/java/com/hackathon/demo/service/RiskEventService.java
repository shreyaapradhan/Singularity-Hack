package com.hackathon.demo.service;

import com.hackathon.demo.model.DangerZone;
import com.hackathon.demo.model.RiskEvent;
import com.hackathon.demo.model.User;
import com.hackathon.demo.repository.RiskEventRepository;
import com.hackathon.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RiskEventService {

    private final RiskEventRepository riskEventRepository;
    private final UserRepository userRepository;

    public RiskEventService(RiskEventRepository riskEventRepository,
                            UserRepository userRepository) {
        this.riskEventRepository = riskEventRepository;
        this.userRepository = userRepository;
    }

    public RiskEvent createRiskEvent(Long userId, DangerZone zone) {

        // prevent duplicate pending risk events
        Optional<RiskEvent> existing =
                riskEventRepository.findByUserIdAndStatus(userId, "PENDING");

        if (existing.isPresent()) {
            return existing.get();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RiskEvent event = new RiskEvent();
        event.setUser(user);
        event.setDangerZone(zone);
        event.setStatus("PENDING");
        event.setEnteredAt(LocalDateTime.now());
        event.setCheckInDeadline(LocalDateTime.now().plusMinutes(15));

        return riskEventRepository.save(event);
    }

    public RiskEvent checkIn(Long riskEventId) {

        RiskEvent event = riskEventRepository.findById(riskEventId)
                .orElseThrow(() -> new RuntimeException("Risk event not found"));

        event.setStatus("SAFE");
        return riskEventRepository.save(event);
    }
}
