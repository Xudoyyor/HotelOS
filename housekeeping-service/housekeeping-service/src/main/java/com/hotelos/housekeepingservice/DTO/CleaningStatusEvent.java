package com.hotelos.housekeepingservice.DTO;

import lombok.*;

import java.io.Serializable;

/**
 * Reception servisiga yuboriladigan tozalash holati hodisasi.
 * status: "TOZALANMOQDA" yoki "TOZA".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CleaningStatusEvent implements Serializable {
    private Long roomId;
    private String roomNumber;
    private String status;
}
