package com.hackathon.demo.repository;

import com.hackathon.demo.model.DangerZone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DangerZoneRepository extends JpaRepository<DangerZone, Long> {
}
