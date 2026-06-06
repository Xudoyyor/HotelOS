package com.hotelos.maintenanceservice.service;

import com.hotelos.maintenanceservice.enums.StatusTask;
import com.hotelos.maintenanceservice.model.Task;
import com.hotelos.maintenanceservice.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final TaskRepository taskRepository;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(MaintenanceService.class);

    private PriorityQueue<Task> taskQueue;

    @PostConstruct
    public void init() {

        Comparator<Task> taskComparator = Comparator
                .comparing((Task t) -> t.getPriority().getValue()).reversed()
                .thenComparing(Task::getReportedAt);

        taskQueue = new PriorityQueue<>(taskComparator);
        List<Task> pendingTasks = taskRepository.findByStatus(StatusTask.PENDING);
        taskQueue.addAll(pendingTasks);
        log.info("Priority Queue ishga tushdi. Navbatdagi vazifalar soni: {}", taskQueue.size());
    }

    @Transactional
    public Task reportIssue(Task task) {
        task.setStatus(StatusTask.PENDING);
        task.setReportedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);


        taskQueue.offer(savedTask);


        rabbitTemplate.convertAndSend("hotel.exchange", "dashboard.maintenance.new",
                "Yangi muammo: Xona " + task.getRoomNumber() + " - " + task.getDescription());

        return savedTask;
    }


    public Task getNextUrgentTask() {
        Task urgentTask = taskQueue.poll();
        if (urgentTask != null) {
            urgentTask.setStatus(StatusTask.IN_PROGRESS);
            return taskRepository.save(urgentTask);
        }
        return null;
    }

    @Transactional
    public Task resolveTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Vazifa topilmadi"));

        task.setStatus(StatusTask.RESOLVED);
        taskQueue.removeIf(t -> t.getId().equals(taskId));

        return taskRepository.save(task);
    }
}