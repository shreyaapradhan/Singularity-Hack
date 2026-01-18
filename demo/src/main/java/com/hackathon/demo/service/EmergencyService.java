package com.hackathon.demo.service;

import com.hackathon.demo.model.SosEvent;
import com.hackathon.demo.model.User;
import com.hackathon.demo.repository.SosEventRepository;
import com.hackathon.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class EmergencyService {

    private final SosEventRepository sosEventRepository;
    private final UserRepository userRepository;

    // userId → check-in start time
    private final Map<Long, Long> dangerCheckTimers = new HashMap<>();

    public EmergencyService(
            SosEventRepository sosEventRepository,
            UserRepository userRepository
    ) {
        this.sosEventRepository = sosEventRepository;
        this.userRepository = userRepository;
    }

    /* =================================================
       CORE SOS TRIGGER (USED BY CALCULATOR & AUTO ESC)
       ================================================= */

    public void triggerSOS(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SosEvent sos = new SosEvent();
        sos.setUser(user);
        sos.setStatus("ACTIVE");
        sos.setTriggeredAt(LocalDateTime.now());

        sosEventRepository.save(sos);

        System.out.println("🚨 SOS TRIGGERED FOR USER ID: " + user.getId());

    }

    /* =================================================
       DANGER ZONE CHECK-IN LOGIC
       ================================================= */

    public void startCheckIn(Long userId) {
        dangerCheckTimers.put(userId, System.currentTimeMillis());
        System.out.println("⚠️ Danger zone entered. Check-in started for userId: " + userId);
    }

    public boolean hasTimedOut(Long userId) {
        Long start = dangerCheckTimers.get(userId);
        if (start == null) return false;

        long minutes =
                (System.currentTimeMillis() - start) / (1000 * 60);

        return minutes >= 15;
    }

    public Set<Long> getTrackedUsers() {
        return dangerCheckTimers.keySet();
    }

    public void autoTriggerSOS(Long userId) {
        System.out.println("⏰ No check-in. Auto-triggering SOS for userId: " + userId);
        triggerSOS(userId);
        dangerCheckTimers.remove(userId);
    }

    public void clearCheckIn(Long userId) {
        dangerCheckTimers.remove(userId);
        System.out.println("✅ Check-in cleared for userId: " + userId);
    }
}
