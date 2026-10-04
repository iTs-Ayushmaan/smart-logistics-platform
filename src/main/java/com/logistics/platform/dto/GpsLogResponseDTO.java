package com.logistics.platform.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GpsLogResponseDTO {

    private Long id;

    private Integer driverId;
    private Integer routeId;
    private Integer currentOrderId;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private BigDecimal speedKmh;
    private BigDecimal accuracyMeters;

    private LocalDateTime timestamp;
}