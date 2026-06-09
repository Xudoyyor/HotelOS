package com.hotelos.authservice.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Login bo'sh bo'lishi mumkin emas.")
        String login,

        @NotBlank(message = "Parol bo'sh bo'lishi mumkin emas.")
        String password
) {
}