package com.logistics.platform.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateOrderDTO {

    // Customer ID is deliberately excluded.
    // It is derived from the authenticated JWT user.

    @NotNull(message = "Pickup latitude is required")
    @DecimalMin("-90")
    @DecimalMax("90")
    private BigDecimal pickupLatitude;

    @NotNull(message = "Pickup longitude is required")
    @DecimalMin("-180")
    @DecimalMax("180")
    private BigDecimal pickupLongitude;

    @NotNull(message = "Delivery latitude is required")
    @DecimalMin("-90")
    @DecimalMax("90")
    private BigDecimal deliveryLatitude;

    @NotNull(message = "Delivery longitude is required")
    @DecimalMin("-180")
    @DecimalMax("180")
    private BigDecimal deliveryLongitude;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotNull(message = "Package weight is required")
    @DecimalMin("0.01")
    private BigDecimal packageWeightKg;

    @NotNull(message = "Package units are required")
    @Min(1)
    private Integer packageUnits;

    @NotNull(message = "Time window start is required")
    private LocalDateTime timeWindowStart;

    @NotNull(message = "Time window end is required")
    private LocalDateTime timeWindowEnd;

    private String priority;

    private String deliveryNotes;
}