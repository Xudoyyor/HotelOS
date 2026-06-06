package com.hotelos.housekeepingservice.component;

import com.hotelos.housekeepingservice.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** Jonli panel uchun strukturalangan ROOM yangilanishlarini nashr etadi. */
@Component
@RequiredArgsConstructor
public class DashboardNotifier {

    private final RabbitTemplate rabbitTemplate;

    public void room(String roomNumber, String status, String detail) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "ROOM");
        payload.put("roomNumber", roomNumber);
        payload.put("status", status);
        payload.put("detail", detail);
        payload.put("timestamp", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_DASHBOARD, payload);
    }
}
