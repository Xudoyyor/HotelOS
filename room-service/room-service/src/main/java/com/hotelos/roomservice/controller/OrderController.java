package com.hotelos.roomservice.controller;

import com.hotelos.roomservice.dto.OrderRequest;
import com.hotelos.roomservice.enums.OrderStatus;
import com.hotelos.roomservice.model.Order;
import com.hotelos.roomservice.service.RoomOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-service/orders")
@RequiredArgsConstructor
public class OrderController {

    private final RoomOrderService roomOrderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(roomOrderService.createOrder(request));
    }

    @PostMapping("/process-next")
    public ResponseEntity<Order> processNext() {
        Order order = roomOrderService.processNextOrder();
        if (order == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(roomOrderService.updateOrderStatus(id, status));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(roomOrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(roomOrderService.getOrder(id));
    }
}