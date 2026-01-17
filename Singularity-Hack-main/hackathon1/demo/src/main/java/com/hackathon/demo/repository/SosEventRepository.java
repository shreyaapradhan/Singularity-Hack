package com.hackathon.demo.repository;

import com.hackathon.demo.model.SosEvent;


import com.hackathon.demo.model.SosEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SosEventRepository extends JpaRepository<SosEvent, Long> {

    // ✅ Correct method for user SOS history
    List<SosEvent> findByUserIdOrderByTriggeredAtDesc(Long userId);
}

