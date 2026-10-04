package com.logistics.platform.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateOrderDTO {

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Pickup latitude is required")
    private BigDecimal pickupLatitude;

    @NotNull(message = "Pickup longitude is required")
    private BigDecimal pickupLongitude;

    @NotNull(message = "Delivery latitude is required")
    private BigDecimal deliveryLatitude;

    @NotNull(message = "Delivery longitude is required")
    private BigDecimal deliveryLongitude;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotNull(message = "Package weight is required")
    @DecimalMin(value = "0.01", message = "Package weight must be greater than 0")
    private BigDecimal packageWeightKg;

    @NotNull(message = "Package units are required")
    private Integer packageUnits;

    @NotNull(message = "Time window start is required")
    private LocalDateTime timeWindowStart;

    @NotNull(message = "Time window end is required")
    private LocalDateTime timeWindowEnd;

    private String priority;

    private String deliveryNotes;
}