package com.logistics.platform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateRouteDTO {

    @NotNull(message = "Driver ID is required")
    private Integer driverId;

    @NotNull(message = "Route date is required")
    private LocalDate routeDate;

    private String status;

    private String routeSequence;

    private String optimizedBy;
}