package com.hotelos.maintenanceservice.controller;

import com.hotelos.maintenanceservice.model.Task;
import com.hotelos.maintenanceservice.model.TaskRequest;
import com.hotelos.maintenanceservice.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maintenance/tasks")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<Task> reportIssue(@RequestBody TaskRequest newTask) {
        Task task = Task.builder()
                .roomNumber(newTask.roomNumber())
                .description(newTask.description())
                .priority(newTask.priority())
                .build();
        return ResponseEntity.ok(maintenanceService.reportIssue(task));
    }

    @GetMapping("/next")
    public ResponseEntity<Task> getNextTask() {
        Task nextTask = maintenanceService.getNextUrgentTask();
        if (nextTask == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(nextTask);
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<Task> resolveTask(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceService.resolveTask(id));
    }



}