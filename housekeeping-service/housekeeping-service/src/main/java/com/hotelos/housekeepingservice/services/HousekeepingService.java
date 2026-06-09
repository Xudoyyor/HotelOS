package com.hotelos.housekeepingservice.services;

import com.hotelos.housekeepingservice.Entities.CleaningTask;
import com.hotelos.housekeepingservice.component.CleaningEventPublisher;
import com.hotelos.housekeepingservice.component.DashboardNotifier;
import com.hotelos.housekeepingservice.enums.CleaningStatus;
import com.hotelos.housekeepingservice.repositories.CleaningTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HousekeepingService {

    private final CleaningTaskRepository repository;
    private final CleaningEventPublisher publisher;
    private final DashboardNotifier dashboardNotifier;

    /** Navbatdagi (NAVBATDA) tozalash vazifalari — FIFO. */
    public List<CleaningTask> getQueue() {
        return repository.findByStatusOrderByCreatedAtAsc(CleaningStatus.NAVBATDA);
    }

    @Transactional
    public CleaningTask startCleaning(Long taskId, String cleaner) {
        CleaningTask task = repository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Vazifa topilmadi: " + taskId));
        task.setStatus(CleaningStatus.TOZALANMOQDA);
        task.setAssignedCleaner(cleaner);
        CleaningTask saved = repository.save(task);
        publisher.publishStarted(saved.getRoomId(), saved.getRoomNumber());
        dashboardNotifier.room(saved.getRoomNumber(), "TOZALANMOQDA", "Tozalash boshlandi: " + cleaner);
        return saved;
    }

    @Transactional
    public CleaningTask completeCleaning(Long taskId) {
        CleaningTask task = repository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Vazifa topilmadi: " + taskId));
        task.setStatus(CleaningStatus.TOZA);
        task.setCompletedAt(LocalDateTime.now());
        CleaningTask saved = repository.save(task);
        publisher.publishCompleted(saved.getRoomId(), saved.getRoomNumber());
        dashboardNotifier.room(saved.getRoomNumber(), "TOZA", "Xona toza, foydalanishga tayyor");
        return saved;
    }
}