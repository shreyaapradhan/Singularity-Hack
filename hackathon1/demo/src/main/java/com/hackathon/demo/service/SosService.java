package com.hackathon.demo.service;

import com.hackathon.demo.model.SosEvent;
import com.hackathon.demo.model.User;
import com.hackathon.demo.repository.SosEventRepository;
import com.hackathon.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SosService {

    private final SosEventRepository sosEventRepository;
    private final UserRepository userRepository;

    public SosService(SosEventRepository sosEventRepository,
                      UserRepository userRepository) {
        this.sosEventRepository = sosEventRepository;
        this.userRepository = userRepository;
    }

    public SosEvent triggerSos(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SosEvent sos = new SosEvent();
        sos.setUser(user);
        sos.setTriggeredAt(LocalDateTime.now());
        sos.setStatus("ACTIVE");

        return sosEventRepository.save(sos);
    }

    public List<SosEvent> getSosHistory(Long userId) {
        return sosEventRepository.findByUserIdOrderByTriggeredAtDesc(userId);
    }
}
