package com.hotelos.maintenanceservice.component;

import com.hotelos.maintenanceservice.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** Jonli panel uchun MAINTENANCE yangilanishlarini nashr etadi. */
@Component
@RequiredArgsConstructor
public class DashboardNotifier {

    private final RabbitTemplate rabbitTemplate;

    public void maintenance(String roomNumber, String status, String detail, String priority) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "MAINTENANCE");
        payload.put("roomNumber", roomNumber);
        payload.put("status", status);
        payload.put("detail", detail);
        payload.put("priority", priority);
        payload.put("timestamp", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_DASHBOARD, payload);
    }
}