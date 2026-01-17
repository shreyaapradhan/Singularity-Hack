package com.hackathon.demo.repository;

import com.hackathon.demo.model.TrustedContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrustedContactRepository extends JpaRepository<TrustedContact, Long> {

    List<TrustedContact> findByUserIdOrderByPriorityOrderAsc(Long userId);
}
