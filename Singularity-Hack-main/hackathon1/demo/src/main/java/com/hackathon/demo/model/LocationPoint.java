package com.hackathon.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "location_points")
public class LocationPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sos_id")
    private SosEvent sosEvent;

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public SosEvent getSosEvent() {
        return sosEvent;
    }

    public void setSosEvent(SosEvent sosEvent) {
        this.sosEvent = sosEvent;
    }
}
