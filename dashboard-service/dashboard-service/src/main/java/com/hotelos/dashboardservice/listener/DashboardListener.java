package com.hotelos.dashboardservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DashboardListener {

    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "dashboard.queue")
    public void receiveMessageFromRabbitMQ(org.springframework.amqp.core.Message message) {
        try {
            // RabbitMQ dan kelgan xabarni baytdan toza String (tekst) holatiga o'giramiz
            String body = new String(message.getBody());
            log.info("RabbitMQ'dan toza tekst keldi: {}", body);

            // WebSocket orqali HTML sahifaga jo'natamiz
            messagingTemplate.convertAndSend("/topic/updates", body);
        } catch (Exception e) {
            log.error("Xabarni qayta ishlashda xato: ", e);
        }
    }
}