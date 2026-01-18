package com.hackathon.demo.service;

import com.hackathon.demo.model.DangerZone;
import com.hackathon.demo.model.LocationPoint;
import com.hackathon.demo.model.RiskEvent;
import com.hackathon.demo.model.SosEvent;
import com.hackathon.demo.repository.LocationPointRepository;
import com.hackathon.demo.repository.SosEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocationService {

    private final LocationPointRepository locationPointRepository;
    private final SosEventRepository sosEventRepository;
    private final DangerZoneDetectionService dangerZoneDetectionService;
    private final RiskEventService riskEventService;

    public LocationService(LocationPointRepository locationPointRepository,
                           SosEventRepository sosEventRepository,
                           DangerZoneDetectionService dangerZoneDetectionService,
                           RiskEventService riskEventService) {
        this.locationPointRepository = locationPointRepository;
        this.sosEventRepository = sosEventRepository;
        this.dangerZoneDetectionService = dangerZoneDetectionService;
        this.riskEventService = riskEventService;
    }

    /**
     * Called every time frontend sends GPS location.
     * This is where AI agent logic runs.
     */
    public LocationPoint saveLocation(Long sosId, double lat, double lng) {

        SosEvent sos = sosEventRepository.findById(sosId)
                .orElseThrow(() -> new RuntimeException("SOS not found"));

        // 1️⃣ Save location
        LocationPoint point = new LocationPoint();
        point.setLatitude(lat);
        point.setLongitude(lng);
        point.setRecordedAt(LocalDateTime.now());
        point.setSosEvent(sos);

        LocationPoint savedPoint = locationPointRepository.save(point);

        // 2️⃣ AI danger zone detection
        DangerZone zone =
                dangerZoneDetectionService.detectDangerZone(lat, lng);

        if (zone != null) {
            // 3️⃣ Create risk event (if not already pending)
            RiskEvent event =
                    riskEventService.createRiskEvent(
                            sos.getUser().getId(),
                            zone
                    );

            System.out.println(
                    "⚠️ RISK ZONE DETECTED for user "
                            + sos.getUser().getId()
                            + " in zone: "
                            + zone.getName()
                            + " | RiskEvent ID: "
                            + event.getId()
            );
        }

        return savedPoint;
    }

    public List<LocationPoint> getLocations(Long sosId) {
        return locationPointRepository
                .findBySosEventIdOrderByRecordedAtAsc(sosId);
    }
}

