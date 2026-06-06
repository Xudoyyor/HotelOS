package com.hotelos.housekeepingservice.Entities;
import com.hotelos.housekeepingservice.enums.CleaningStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cleaning_tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CleaningTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId; // Reception DB dagi xona IDsi bilan bog'lanadi

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CleaningStatus status; // NAVBATDA -> TOZALANMOQDA -> TOZA

    @Column(name = "assigned_cleaner")
    private String assignedCleaner; // Biriktirilgan farrosh ismi

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
