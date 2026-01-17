package com.hackathon.demo.repository;

import com.hackathon.demo.model.LocationPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationPointRepository
        extends JpaRepository<LocationPoint, Long> {
}
