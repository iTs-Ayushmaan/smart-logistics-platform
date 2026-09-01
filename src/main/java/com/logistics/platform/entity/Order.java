package com.logistics.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "pickup_latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal pickupLatitude;

    @Column(name = "pickup_longitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal pickupLongitude;

    @Column(name = "delivery_latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal deliveryLatitude;

    @Column(name = "delivery_longitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal deliveryLongitude;

    @Column(name = "delivery_address", nullable = false, columnDefinition = "TEXT")
    private String deliveryAddress;

    @Column(name = "package_weight_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal packageWeightKg;

    @Column(name = "package_units", nullable = false)
    private Integer packageUnits;

    @Column(name = "time_window_start", nullable = false)
    private LocalDateTime timeWindowStart;

    @Column(name = "time_window_end", nullable = false)
    private LocalDateTime timeWindowEnd;

    @Column(length = 50)
    private String priority = "NORMAL";

    @Column(length = 50)
    private String status = "PENDING";

    @Column(name = "assigned_driver_id")
    private Integer assignedDriverId;

    @Column(name = "assigned_route_id")
    private Integer assignedRouteId;

    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    @Column(name = "actual_delivery_time")
    private LocalDateTime actualDeliveryTime;

    @Column(name = "delivery_notes", columnDefinition = "TEXT")
    private String deliveryNotes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}