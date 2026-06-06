package com.hotelos.housekeepingservice.repositories;

import com.hotelos.housekeepingservice.Entities.CleaningTask;
import com.hotelos.housekeepingservice.enums.CleaningStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CleaningTaskRepository extends JpaRepository<CleaningTask,Long> {
    Optional<CleaningTask> findByRoomIdAndStatus(Long roomId, CleaningStatus status);

    /** Tozalash navbatini (eng eski birinchi) qaytaradi - FIFO tartib. */
    List<CleaningTask> findByStatusOrderByCreatedAtAsc(CleaningStatus status);
}
