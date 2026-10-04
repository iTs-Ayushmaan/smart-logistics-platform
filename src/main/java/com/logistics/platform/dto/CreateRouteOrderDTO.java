package com.logistics.platform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRouteOrderDTO {

    @NotNull(message = "Route ID is required")
    private Integer routeId;

    @NotNull(message = "Order ID is required")
    private Integer orderId;

    @NotNull(message = "Sequence number is required")
    private Integer sequenceNumber;

    private String status;
}