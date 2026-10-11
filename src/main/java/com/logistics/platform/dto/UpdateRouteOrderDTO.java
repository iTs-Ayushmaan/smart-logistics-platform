package com.logistics.platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRouteOrderDTO {

    @Min(value = 1, message = "Sequence number must be at least 1")
    private Integer sequenceNumber;

    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;
}