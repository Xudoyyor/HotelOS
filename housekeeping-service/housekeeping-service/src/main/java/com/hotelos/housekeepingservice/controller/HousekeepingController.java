package com.hotelos.housekeepingservice.controller;

import com.hotelos.housekeepingservice.Entities.CleaningTask;
import com.hotelos.housekeepingservice.services.HousekeepingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/housekeeping")
@RequiredArgsConstructor
public class HousekeepingController {

    private final HousekeepingService service;

    @GetMapping("/queue")
    public ResponseEntity<List<CleaningTask>> queue() {
        return ResponseEntity.ok(service.getQueue());
    }

    @PutMapping("/tasks/{id}/start")
    public ResponseEntity<CleaningTask> start(@PathVariable Long id,
                                              @RequestParam(defaultValue = "Navbatchi farrosh") String cleaner) {
        return ResponseEntity.ok(service.startCleaning(id, cleaner));
    }

    @PutMapping("/tasks/{id}/complete")
    public ResponseEntity<CleaningTask> complete(@PathVariable Long id) {
        return ResponseEntity.ok(service.completeCleaning(id));
    }
}