package com.hackathon.demo.repository;

import com.hackathon.demo.model.SosEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SosEventRepository extends JpaRepository<SosEvent, Long> {

    List<SosEvent> findByUserIdOrderByTriggeredAtDesc(Long userId);
}
