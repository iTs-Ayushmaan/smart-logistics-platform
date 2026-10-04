package com.logistics.platform.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateOrderDTO {

    private BigDecimal pickupLatitude;

    private BigDecimal pickupLongitude;

    private BigDecimal deliveryLatitude;

    private BigDecimal deliveryLongitude;

    private String deliveryAddress;

    @DecimalMin(value = "0.01", message = "Package weight must be greater than 0")
    private BigDecimal packageWeightKg;

    private Integer packageUnits;

    private LocalDateTime timeWindowStart;

    private LocalDateTime timeWindowEnd;

    private String priority;

    private String status;

    private Integer assignedDriverId;

    private Integer assignedRouteId;

    private String deliveryNotes;
}