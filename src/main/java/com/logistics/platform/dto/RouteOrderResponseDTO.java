package com.logistics.platform.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteOrderResponseDTO {

    private Integer id;
    private Integer routeId;
    private Integer orderId;
    private Integer sequenceNumber;

    private LocalDateTime estimatedArrivalTime;
    private LocalDateTime actualArrivalTime;

    private LocalDateTime estimatedDepartureTime;
    private LocalDateTime actualDepartureTime;

    private String status;
    private LocalDateTime createdAt;
}