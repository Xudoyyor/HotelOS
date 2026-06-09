package com.hotelos.housekeepingservice.component;

import com.hotelos.housekeepingservice.DTO.CleaningStatusEvent;
import com.hotelos.housekeepingservice.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CleaningEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishStarted(Long roomId, String roomNumber) {
        CleaningStatusEvent e = CleaningStatusEvent.builder()
                .roomId(roomId).roomNumber(roomNumber).status("TOZALANMOQDA").build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_CLEANING_STARTED, e);
    }

    public void publishCompleted(Long roomId, String roomNumber) {
        CleaningStatusEvent e = CleaningStatusEvent.builder()
                .roomId(roomId).roomNumber(roomNumber).status("TOZA").build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_CLEANING_COMPLETED, e);
    }
}