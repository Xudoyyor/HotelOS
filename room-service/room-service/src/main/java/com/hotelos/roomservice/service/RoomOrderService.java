package com.hotelos.roomservice.service;

import com.hotelos.roomservice.model.Order;
import com.hotelos.roomservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RoomOrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "hotel.exchange";

    public Order createOrder(Order order) {
        order.setStatus("QABUL_QILINDI");
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // RabbitMQ-ga dashboard yangilanishi haqida xabar jo'natamiz
        publishStatusUpdate(savedOrder);
        return savedOrder;
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Buyurtma topilmadi"));

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        publishStatusUpdate(updatedOrder);
        return updatedOrder;
    }

    private void publishStatusUpdate(Order order) {
        String message = String.format("Xona %s buyurtmasi holati: %s", order.getRoomNumber(), order.getStatus());
        rabbitTemplate.convertAndSend(EXCHANGE, "dashboard.order.update", message);
    }
}