package com.hotelos.maintenanceservice.model;

import com.hotelos.maintenanceservice.enums.Priority;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

public record TaskRequest(
       @NotNull
        String roomNumber,
        @NotBlank
        String description,
        @Valid
        Priority priority
) {
}
