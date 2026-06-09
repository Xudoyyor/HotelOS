package com.hotelos.maintenanceservice.enums;

/**
 * Nosozlik ustuvorligi. Raqamli qiymat PriorityQueue tartiblashda ishlatiladi
 * (qiymat qancha katta bo'lsa, shuncha shoshilinch).
 */
public enum Priority {
    CRITICAL(4),
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private final int value;

    Priority(int value) { this.value = value; }

    public int getValue() { return value; }
}