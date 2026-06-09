package com.hotelos.maintenanceservice.service;

import com.hotelos.maintenanceservice.component.DashboardNotifier;
import com.hotelos.maintenanceservice.enums.StatusTask;
import com.hotelos.maintenanceservice.exceptions.ResourceNotFoundException;
import com.hotelos.maintenanceservice.model.Task;
import com.hotelos.maintenanceservice.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Texnik xizmat vazifalarini boshqaruvchi servis.
 * MA'LUMOTLAR TUZILMASI: PriorityQueue - eng shoshilinch (CRITICAL) vazifa birinchi chiqadi.
 * HODISAGA ASOSLANGAN: har bir o'zgarish jonli panelga nashr etiladi.
 */
@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceService.class);

    private final TaskRepository taskRepository;
    private final DashboardNotifier dashboardNotifier;

    private PriorityQueue<Task> taskQueue;

    @PostConstruct
    public void init() {
        Comparator<Task> taskComparator = Comparator
                .comparing((Task t) -> t.getPriority().getValue()).reversed()
                .thenComparing(Task::getReportedAt);

        taskQueue = new PriorityQueue<>(taskComparator);
        List<Task> pendingTasks = taskRepository.findByStatus(StatusTask.PENDING);
        taskQueue.addAll(pendingTasks);
        log.info("Ustuvorlik navbati ishga tushdi. Kutilayotgan vazifalar: {}", taskQueue.size());
    }

    @Transactional
    public Task reportIssue(Task task) {
        task.setStatus(StatusTask.PENDING);
        task.setReportedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);
        taskQueue.offer(savedTask);

        dashboardNotifier.maintenance(savedTask.getRoomNumber(), savedTask.getStatus().name(),
                savedTask.getDescription(), savedTask.getPriority().name());
        return savedTask;
    }

    /** Navbatdagi eng shoshilinch vazifani texnikka biriktiradi. */
    @Transactional
    public Task assignNextTask(String technician) {
        Task urgentTask = taskQueue.poll();
        if (urgentTask == null) {
            return null;
        }
        urgentTask.setStatus(StatusTask.IN_PROGRESS);
        urgentTask.setAssignedTechnician(technician);
        Task saved = taskRepository.save(urgentTask);

        dashboardNotifier.maintenance(saved.getRoomNumber(), saved.getStatus().name(),
                saved.getDescription(), saved.getPriority().name());
        return saved;
    }

    @Transactional
    public Task resolveTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Vazifa topilmadi: " + taskId));

        task.setStatus(StatusTask.RESOLVED);
        task.setResolvedAt(LocalDateTime.now());
        taskQueue.removeIf(t -> t.getId().equals(taskId));
        Task saved = taskRepository.save(task);

        dashboardNotifier.maintenance(saved.getRoomNumber(), saved.getStatus().name(),
                saved.getDescription(), saved.getPriority().name());
        return saved;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}