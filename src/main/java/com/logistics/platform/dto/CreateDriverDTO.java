package com.logistics.platform.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
public class CreateDriverDTO {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Integer userId;

    @NotBlank(message = "Driver name is required")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "^\\+?[0-9 ()-]{7,20}$",
            message = "Invalid phone number"
    )
    private String phone;

    @Size(max = 50)
    private String vehicleType;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal vehicleCapacityKg;

    @NotNull
    @Min(1)
    private Integer vehicleCapacityUnits;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private BigDecimal currentLatitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private BigDecimal currentLongitude;

    private String status;

    private LocalTime availabilityStartTime;

    private LocalTime availabilityEndTime;
}