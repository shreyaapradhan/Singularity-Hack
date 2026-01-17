package com.hackathon.demo.controller;

import com.hackathon.demo.model.SosEvent;
import com.hackathon.demo.service.SosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sos")
public class SosController {

    private final SosService sosService;

    public SosController(SosService sosService) {
        this.sosService = sosService;
    }

    @PostMapping("/trigger/{userId}")
    public SosEvent triggerSos(@PathVariable Long userId) {
        return sosService.triggerSos(userId);
    }

    @GetMapping("/history/{userId}")
    public List<SosEvent> getHistory(@PathVariable Long userId) {
        return sosService.getSosHistory(userId);
    }
}
