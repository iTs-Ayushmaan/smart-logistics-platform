package com.logistics.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "vehicle_type", length = 50)
    private String vehicleType;

    @Column(name = "vehicle_capacity_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal vehicleCapacityKg;

    @Column(name = "vehicle_capacity_units", nullable = false)
    private Integer vehicleCapacityUnits;

    @Column(name = "current_latitude", precision = 10, scale = 8)
    private BigDecimal currentLatitude;

    @Column(name = "current_longitude", precision = 10, scale = 8)
    private BigDecimal currentLongitude;

    @Column(length = 50)
    private String status = "INACTIVE";

    @Column(name = "availability_start_time")
    private LocalTime availabilityStartTime;

    @Column(name = "availability_end_time")
    private LocalTime availabilityEndTime;

    @Column(name = "total_deliveries")
    private Integer totalDeliveries = 0;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}