package com.hackathon.demo.controller;

import com.hackathon.demo.model.DangerZone;
import com.hackathon.demo.service.DangerZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danger-zones")
@CrossOrigin
public class DangerZoneController {

    private final DangerZoneService dangerZoneService;

    public DangerZoneController(DangerZoneService dangerZoneService) {
        this.dangerZoneService = dangerZoneService;
    }

    @PostMapping
    public DangerZone addZone(@RequestBody DangerZone zone) {
        return dangerZoneService.saveZone(zone);
    }

    @GetMapping
    public List<DangerZone> getAllZones() {
        return dangerZoneService.getAllZones();
    }
}
