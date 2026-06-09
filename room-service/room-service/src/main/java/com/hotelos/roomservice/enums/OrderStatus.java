package com.hotelos.roomservice.enums;

/**
 * Buyurtma hayotiy sikli (STATE MACHINE).
 * Holatlar faqat oldinga (ketma-ket) o'tishi mumkin: noto'g'ri o'tishlar bloklanadi.
 */
public enum OrderStatus {
    QABUL_QILINDI,
    TAYYORLANMOQDA,
    YETKAZILMOQDA,
    YETKAZILDI;

    /** Faqat bevosita keyingi bosqichga o'tishga ruxsat beradi. */
    public boolean canTransitionTo(OrderStatus next) {
        return next != null && next.ordinal() == this.ordinal() + 1;
    }
}