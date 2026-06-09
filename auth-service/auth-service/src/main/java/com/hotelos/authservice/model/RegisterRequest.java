package com.hotelos.authservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Foydalanuvchi nomi bo'sh bo'lishi mumkin emas.")
        String username,

        @NotBlank(message = "Parol bo'sh bo'lishi mumkin emas.")
        @Size(min = 4, message = "Parol kamida 4 ta belgidan iborat bo'lishi kerak.")
        String password,

        @NotNull(message = "Rol ko'rsatilishi shart.")
        Role role
) {
}