package com.hackathon.demo.controller;

import com.hackathon.demo.model.LocationPoint;
import com.hackathon.demo.model.SosEvent;
import com.hackathon.demo.repository.LocationPointRepository;
import com.hackathon.demo.repository.SosEventRepository;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final SosEventRepository sosEventRepository;
    private final LocationPointRepository locationPointRepository;

    public LocationController(
            SosEventRepository sosEventRepository,
            LocationPointRepository locationPointRepository) {
        this.sosEventRepository = sosEventRepository;
        this.locationPointRepository = locationPointRepository;
    }

    @PostMapping("/{sosId}")
    public void saveLocation(
            @PathVariable Long sosId,
            @RequestParam double latitude,
            @RequestParam double longitude) {

        System.out.println("📍 LOCATION RECEIVED");
        System.out.println("SOS ID: " + sosId);
        System.out.println("LAT: " + latitude + " LNG: " + longitude);

        SosEvent sosEvent = sosEventRepository.findById(sosId)
                .orElseThrow(() -> new RuntimeException("SOS EVENT NOT FOUND"));

        LocationPoint point = new LocationPoint();
        point.setLatitude(latitude);
        point.setLongitude(longitude);
        point.setTimestamp(LocalDateTime.now());   // ✅ CORRECT METHOD
        point.setSosEvent(sosEvent);

        locationPointRepository.save(point);

        System.out.println("✅ LOCATION SAVED TO DATABASE");
    }
}
