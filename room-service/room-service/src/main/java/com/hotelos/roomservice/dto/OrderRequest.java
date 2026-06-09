package com.hotelos.roomservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

/** Mijozdan keladigan buyurtma so'rovi (kiruvchi ma'lumot validatsiyalanadi). */
public record OrderRequest(
        @NotNull(message = "Xona ID si ko'rsatilishi shart.")
        Long roomId,

        @NotBlank(message = "Xona raqami bo'sh bo'lishi mumkin emas.")
        @Pattern(regexp = "\\d{3}", message = "Xona raqami 3 xonali son bo'lishi kerak (masalan 101).")
        String roomNumber,

        @NotEmpty(message = "Buyurtmada kamida bitta taom bo'lishi kerak.")
        List<String> items
) {
}