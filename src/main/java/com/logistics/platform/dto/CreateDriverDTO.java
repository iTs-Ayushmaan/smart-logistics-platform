package com.logistics.platform.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
public class CreateDriverDTO {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "Driver name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Phone is required")
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    @Size(max = 50, message = "Vehicle type must not exceed 50 characters")
    private String vehicleType;

    @NotNull(message = "Vehicle capacity in kg is required")
    @DecimalMin(value = "0.01", message = "Vehicle capacity must be greater than 0")
    private BigDecimal vehicleCapacityKg;

    @NotNull(message = "Vehicle capacity in units is required")
    private Integer vehicleCapacityUnits;

    private BigDecimal currentLatitude;

    private BigDecimal currentLongitude;

    private String status;

    private LocalTime availabilityStartTime;

    private LocalTime availabilityEndTime;
}