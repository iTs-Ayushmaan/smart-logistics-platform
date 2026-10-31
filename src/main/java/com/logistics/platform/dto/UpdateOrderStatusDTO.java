package com.logistics.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusDTO {

    @NotBlank(message = "Status is required")
    private String status;

    private String deliveryNotes;
}