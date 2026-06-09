package com.hotelos.roomservice.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Buyurtma yetkazilgach reception servisiga yuboriladigan to'lov hodisasi.
 * Maydon nomlari reception tomonidagi DTO bilan AYNAN mos kelishi shart.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderChargeEvent implements Serializable {
    private String roomNumber;
    private String description;
    private BigDecimal amount;
}