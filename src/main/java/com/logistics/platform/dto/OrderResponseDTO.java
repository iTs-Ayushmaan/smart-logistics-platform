package com.logistics.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {

    private Integer id;
    private Integer customerId;

    private BigDecimal pickupLatitude;
    private BigDecimal pickupLongitude;

    private BigDecimal deliveryLatitude;
    private BigDecimal deliveryLongitude;

    private String deliveryAddress;

    private BigDecimal packageWeightKg;
    private Integer packageUnits;

    private LocalDateTime timeWindowStart;
    private LocalDateTime timeWindowEnd;

    private String priority;
    private String status;

    private Integer assignedDriverId;
    private Integer assignedRouteId;

    private LocalDateTime pickupTime;
    private LocalDateTime actualDeliveryTime;

    private String deliveryNotes;
}