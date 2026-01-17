package com.hackathon.demo.service;

import com.hackathon.demo.model.DangerZone;
import com.hackathon.demo.util.GeoUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DangerZoneDetectionService {

    private final DangerZoneService dangerZoneService;

    public DangerZoneDetectionService(DangerZoneService dangerZoneService) {
        this.dangerZoneService = dangerZoneService;
    }

    /**
     * Checks if given location is inside any danger zone.
     * Returns the zone if found, else null.
     */
    public DangerZone detectDangerZone(double userLat, double userLng) {

        List<DangerZone> zones = dangerZoneService.getAllZones();

        for (DangerZone zone : zones) {

            double distance = GeoUtils.distanceInMeters(
                    userLat,
                    userLng,
                    zone.getLatitude(),
                    zone.getLongitude()
            );

            if (distance <= zone.getRadiusMeters()) {
                return zone; // ENTERED danger zone
            }
        }

        return null; // SAFE
    }
}
