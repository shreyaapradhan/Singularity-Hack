package com.hackathon.demo.service;

import com.hackathon.demo.model.LocationPoint;
import com.hackathon.demo.model.SosEvent;
import com.hackathon.demo.repository.LocationPointRepository;
import com.hackathon.demo.repository.SosEventRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationService {

    private final LocationPointRepository locationPointRepository;
    private final SosEventRepository sosEventRepository;

    public LocationService(
            LocationPointRepository locationPointRepository,
            SosEventRepository sosEventRepository) {
        this.locationPointRepository = locationPointRepository;
        this.sosEventRepository = sosEventRepository;
    }

    /**
     * Saves a single GPS point linked to an SOS event
     */
    public void saveLocation(Long sosId, double latitude, double longitude) {

        SosEvent sosEvent = sosEventRepository.findById(sosId)
                .orElseThrow(() -> new RuntimeException("SOS EVENT NOT FOUND"));

        LocationPoint point = new LocationPoint();
        point.setLatitude(latitude);
        point.setLongitude(longitude);
        point.setTimestamp(LocalDateTime.now()); // ✅ CORRECT FIELD
        point.setSosEvent(sosEvent);

        locationPointRepository.save(point);

        System.out.println("📍 LOCATION SAVED → SOS ID " + sosId);
    }
}
