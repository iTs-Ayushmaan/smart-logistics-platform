package com.logistics.platform.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateOrderDTO {

    @DecimalMin("-90")
    @DecimalMax("90")
    private BigDecimal pickupLatitude;

    @DecimalMin("-180")
    @DecimalMax("180")
    private BigDecimal pickupLongitude;

    @DecimalMin("-90")
    @DecimalMax("90")
    private BigDecimal deliveryLatitude;

    @DecimalMin("-180")
    @DecimalMax("180")
    private BigDecimal deliveryLongitude;

//    @NotBlank(message = "Delivery address cannot be blank")
    private String deliveryAddress;

    @DecimalMin("0.01")
    private BigDecimal packageWeightKg;

    @Min(1)
    private Integer packageUnits;

    private LocalDateTime timeWindowStart;

    private LocalDateTime timeWindowEnd;

    private String priority;

    private String status;

    private Integer assignedDriverId;

    private Integer assignedRouteId;

    private String deliveryNotes;
}