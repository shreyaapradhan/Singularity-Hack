package com.hackathon.demo.service;

import com.hackathon.demo.model.RiskEvent;
import com.hackathon.demo.model.SosEvent;
import com.hackathon.demo.model.TrustedContact;
import com.hackathon.demo.repository.RiskEventRepository;
import com.hackathon.demo.repository.SosEventRepository;
import com.hackathon.demo.repository.TrustedContactRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RiskEscalationService {

    private final RiskEventRepository riskEventRepository;
    private final SosEventRepository sosEventRepository;
    private final TrustedContactRepository trustedContactRepository;
    private final NotificationService notificationService;
    private final GenAIService genAIService;

    public RiskEscalationService(
            RiskEventRepository riskEventRepository,
            SosEventRepository sosEventRepository,
            TrustedContactRepository trustedContactRepository,
            NotificationService notificationService,
            GenAIService genAIService) {

        this.riskEventRepository = riskEventRepository;
        this.sosEventRepository = sosEventRepository;
        this.trustedContactRepository = trustedContactRepository;
        this.notificationService = notificationService;
        this.genAIService = genAIService;
    }

    // Runs every 1 minute
    @Scheduled(fixedRate = 60000)
    public void autoEscalate() {

        List<RiskEvent> overdueEvents =
                riskEventRepository.findByStatusAndCheckInDeadlineBefore(
                        "PENDING",
                        LocalDateTime.now()
                );

        for (RiskEvent event : overdueEvents) {

            // 1️⃣ Mark as escalated
            event.setStatus("ESCALATED");
            riskEventRepository.save(event);

            // 2️⃣ Create SOS
            SosEvent sos = new SosEvent();
            sos.setUser(event.getUser());
            sos.setTriggeredAt(LocalDateTime.now());
            sos.setStatus("ACTIVE");

            sosEventRepository.save(sos);

            // 3️⃣ Fetch trusted contacts
            List<TrustedContact> contacts =
                    trustedContactRepository
                            .findByUserIdOrderByPriorityOrderAsc(
                                    event.getUser().getId()
                            );

            // 4️⃣ Generate GenAI alert message
            String message =
                    genAIService.generateAlertMessage(
                            event.getUser(),
                            event.getDangerZone(),
                            event
                    );

            // 5️⃣ Notify contacts
            notificationService.notifyContacts(
                    event.getUser(),
                    contacts,
                    message
            );

            System.out.println(
                    "🚨 AUTO-ESCALATION COMPLETE for user ID: "
                            + event.getUser().getId()
            );
        }
    }
}

