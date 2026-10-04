package com.logistics.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverResponseDTO {

    private Integer id;
    private Integer userId;
    private String name;
    private String phone;
    private String vehicleType;
    private BigDecimal vehicleCapacityKg;
    private Integer vehicleCapacityUnits;
    private BigDecimal currentLatitude;
    private BigDecimal currentLongitude;
    private String status;
    private LocalTime availabilityStartTime;
    private LocalTime availabilityEndTime;
    private Integer totalDeliveries;
    private BigDecimal rating;
}