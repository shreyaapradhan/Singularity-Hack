package com.hackathon.demo.controller;

import com.hackathon.demo.model.RiskEvent;
import com.hackathon.demo.service.RiskEventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
public class RiskEventController {

    private final RiskEventService riskEventService;

    public RiskEventController(RiskEventService riskEventService) {
        this.riskEventService = riskEventService;
    }

    @PostMapping("/check-in/{riskEventId}")
    public RiskEvent checkIn(@PathVariable Long riskEventId) {
        return riskEventService.checkIn(riskEventId);
    }
}
