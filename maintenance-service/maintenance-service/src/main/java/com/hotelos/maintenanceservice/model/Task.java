package com.hotelos.maintenanceservice.model;

import com.hotelos.maintenanceservice.enums.Priority;
import com.hotelos.maintenanceservice.enums.StatusTask;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number")
    private String roomNumber;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private StatusTask status;

    @Column(name = "reported_at")
    private LocalDateTime reportedAt;
}