package com.hotelos.housekeepingservice.services;
import com.hotelos.housekeepingservice.DTO.RoomVacatedEvent;
import com.hotelos.housekeepingservice.Entities.CleaningTask;
import com.hotelos.housekeepingservice.config.RabbitMQConfig;
import com.hotelos.housekeepingservice.enums.CleaningStatus;
import com.hotelos.housekeepingservice.repositories.CleaningTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class HousekeepingListener {

    private final CleaningTaskRepository cleaningTaskRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleRoomVacated(RoomVacatedEvent event) {
        log.info("Reception dan xabar keldi: Xona bo'shadi -> Xona raqami: {}", event.getRoomNumber());

        // Yangi tozalash vazifasini yaratish va bazaga saqlash
        CleaningTask task = CleaningTask.builder()
                .roomId(event.getRoomId())
                .roomNumber(event.getRoomNumber())
                .status(CleaningStatus.NAVBATDA)
                .createdAt(LocalDateTime.now())
                .build();

        cleaningTaskRepository.save(task);
        log.info("Xona {} muvaffaqiyatli tozalash navbatiga qo'shildi .", event.getRoomNumber());
    }
}