package com.logistics.platform.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
public class UpdateDriverDTO {

    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    @Size(max = 50, message = "Vehicle type must not exceed 50 characters")
    private String vehicleType;

    @DecimalMin(value = "0.01", message = "Vehicle capacity must be greater than 0")
    private BigDecimal vehicleCapacityKg;

    private Integer vehicleCapacityUnits;

    private BigDecimal currentLatitude;

    private BigDecimal currentLongitude;

    private String status;

    private LocalTime availabilityStartTime;

    private LocalTime availabilityEndTime;
}