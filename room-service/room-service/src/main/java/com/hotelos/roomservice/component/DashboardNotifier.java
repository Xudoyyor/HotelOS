package com.hotelos.roomservice.component;

import com.hotelos.roomservice.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** Jonli panel uchun ORDER yangilanishlarini nashr etadi (HODISAGA ASOSLANGAN). */
@Component
@RequiredArgsConstructor
public class DashboardNotifier {

    private final RabbitTemplate rabbitTemplate;

    public void order(String roomNumber, String status, String detail, BigDecimal amount) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "ORDER");
        payload.put("roomNumber", roomNumber);
        payload.put("status", status);
        payload.put("detail", detail);
        if (amount != null) {
            payload.put("amount", amount);
        }
        payload.put("timestamp", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_DASHBOARD, payload);
    }
}