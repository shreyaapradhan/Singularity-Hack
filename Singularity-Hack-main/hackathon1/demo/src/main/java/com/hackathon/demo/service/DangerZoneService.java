package com.hackathon.demo.service;

import com.hackathon.demo.model.DangerZone;
import com.hackathon.demo.repository.DangerZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DangerZoneService {

    private final DangerZoneRepository dangerZoneRepository;

    public DangerZoneService(DangerZoneRepository dangerZoneRepository) {
        this.dangerZoneRepository = dangerZoneRepository;
    }

    public DangerZone saveZone(DangerZone zone) {
        return dangerZoneRepository.save(zone);
    }

    public List<DangerZone> getAllZones() {
        return dangerZoneRepository.findAll();
    }
}

