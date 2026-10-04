package com.logistics.platform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateGpsLogDTO {

    @NotNull(message = "Driver ID is required")
    private Integer driverId;

    private Integer routeId;

    private Integer currentOrderId;

    @NotNull(message = "Latitude is required")
    private BigDecimal latitude;

    @NotNull(message = "Longitude is required")
    private BigDecimal longitude;

    private BigDecimal speedKmh;

    private BigDecimal accuracyMeters;
}