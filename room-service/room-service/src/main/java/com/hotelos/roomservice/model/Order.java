package com.hotelos.roomservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;
    private String roomNumber;
    private String items; // Masalan: "2 ta qahva, 1 ta sendvich"
    private Double totalAmount;
    private String status; // QABUL_QILINDI, TAYYORLANMOQDA, YETKAZILDI
    private LocalDateTime createdAt;
}