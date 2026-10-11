package com.logistics.platform.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateRouteDTO {

    private LocalDate routeDate;

    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;

    private String routeSequence;

    @Size(max = 50, message = "Optimized by must not exceed 50 characters")
    private String optimizedBy;
}