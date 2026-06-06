package com.hotelos.housekeepingservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomVacatedEvent implements Serializable {
    private Long roomId;
    private String roomNumber;
    private String message;
}
