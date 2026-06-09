package com.hotelos.maintenanceservice.model;

import com.hotelos.maintenanceservice.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Yangi nosozlik haqidagi so'rov (kiruvchi ma'lumot validatsiyalanadi). */
public record TaskRequest(
        @NotBlank(message = "Xona raqami bo'sh bo'lishi mumkin emas.")
        String roomNumber,

        @NotBlank(message = "Muammo tavsifi bo'sh bo'lishi mumkin emas.")
        String description,

        @NotNull(message = "Ustuvorlik darajasi ko'rsatilishi shart.")
        Priority priority
) {
}