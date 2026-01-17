package com.hackathon.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_events")
public class RiskEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // PENDING, SAFE, ESCALATED
    private String status;

    private LocalDateTime enteredAt;
    private LocalDateTime checkInDeadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private DangerZone dangerZone;

    // getters & setters
    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getEnteredAt() {
        return enteredAt;
    }

    public LocalDateTime getCheckInDeadline() {
        return checkInDeadline;
    }

    public User getUser() {
        return user;
    }

    public DangerZone getDangerZone() {
        return dangerZone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEnteredAt(LocalDateTime enteredAt) {
        this.enteredAt = enteredAt;
    }

    public void setCheckInDeadline(LocalDateTime checkInDeadline) {
        this.checkInDeadline = checkInDeadline;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDangerZone(DangerZone dangerZone) {
        this.dangerZone = dangerZone;
    }
}

