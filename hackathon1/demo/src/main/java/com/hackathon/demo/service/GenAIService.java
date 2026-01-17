package com.hackathon.demo.service;

import com.hackathon.demo.model.DangerZone;
import com.hackathon.demo.model.RiskEvent;
import com.hackathon.demo.model.User;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class GenAIService {

    /**
     * Generates a human-friendly alert message
     * for trusted contacts.
     */
    public String generateAlertMessage(User user,
                                       DangerZone zone,
                                       RiskEvent event) {

        String time =
                event.getEnteredAt()
                        .format(DateTimeFormatter.ofPattern("hh:mm a"));

        return "🚨 EMERGENCY ALERT\n\n"
                + user.getFullname()
                + " entered a "
                + zone.getRiskLevel().toLowerCase()
                + "-risk area ("
                + zone.getName()
                + ") at "
                + time
                + ".\n\n"
                + "No safety check-in was received within 15 minutes.\n"
                + "Live location sharing has been activated automatically.\n\n"
                + "Please try to contact her immediately.";
    }

    /**
     * Generates a structured incident summary
     * (for NGO / Police / Reports).
     */
    public String generateIncidentSummary(User user,
                                          DangerZone zone,
                                          RiskEvent event) {

        return "INCIDENT SUMMARY\n"
                + "-----------------\n"
                + "User: " + user.getFullname() + "\n"
                + "Phone: " + user.getMobile() + "\n"
                + "Risk Zone: " + zone.getName() + "\n"
                + "Risk Level: " + zone.getRiskLevel() + "\n"
                + "Entered At: " + event.getEnteredAt() + "\n"
                + "Check-in Deadline: " + event.getCheckInDeadline() + "\n"
                + "Final Status: " + event.getStatus() + "\n\n"
                + "Action Taken:\n"
                + "- Automated danger detection\n"
                + "- Check-in window provided\n"
                + "- Automatic escalation triggered\n"
                + "- Trusted contacts notified\n"
                + "- Live location sharing enabled\n";
    }
}