package com.hotelos.dashboardservice.listener;

import com.hotelos.dashboardservice.state.DashboardState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * hotel.exchange'dan kelgan dashboard.update hodisalarini tinglaydi,
 * markaziy holatni yangilaydi va to'liq snapshot'ni WebSocket orqali yuboradi.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DashboardListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final DashboardState dashboardState;

    @RabbitListener(queues = "dashboard.queue")
    public void onDashboardEvent(Map<String, Object> event) {
        try {
            log.info("Panel hodisasi keldi: {}", event);
            dashboardState.apply(event);
            messagingTemplate.convertAndSend("/topic/updates", (Object) dashboardState.snapshot());
        } catch (Exception e) {
            log.error("Panel hodisasini qayta ishlashda xato: ", e);
        }
    }
}