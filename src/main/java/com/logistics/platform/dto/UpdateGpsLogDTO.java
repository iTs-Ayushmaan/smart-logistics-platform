package com.logistics.platform.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateGpsLogDTO {

    private BigDecimal latitude;

    private BigDecimal longitude;

    @DecimalMin(value = "0.0", message = "Speed cannot be negative")
    private BigDecimal speedKmh;

    @DecimalMin(value = "0.0", message = "Accuracy cannot be negative")
    private BigDecimal accuracyMeters;
}