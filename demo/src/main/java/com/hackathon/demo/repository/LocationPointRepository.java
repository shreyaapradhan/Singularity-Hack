package com.hackathon.demo.repository;

import com.hackathon.demo.model.LocationPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationPointRepository
        extends JpaRepository<LocationPoint, Long> {

    List<LocationPoint> findBySosEventIdOrderByRecordedAtAsc(Long sosId);
}
