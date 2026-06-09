package com.hotelos.authservice.domain;

import com.hotelos.authservice.model.Role;

import java.util.List;

/**
 * FACTORY pattern: Role enum asosida mos Employee avlodini yaratadi.
 */
public final class EmployeeFactory {

    private EmployeeFactory() {}

    public static Employee create(Role role, String username) {
        if (role == null) {
            return new BasicUser(username);
        }
        return switch (role) {
            case RECEPTION -> new Receptionist(username);
            case CLEANER -> new Cleaner(username);
            case MAINTENANCE -> new Technician(username);
            case ADMIN -> new Administrator(username);
            case USER -> new BasicUser(username);
        };
    }

    /** Oddiy (cheklangan) foydalanuvchi. */
    private static final class BasicUser extends Employee {
        private BasicUser(String username) { super(username); }
        @Override public String getRole() { return "USER"; }
        @Override public String getTitle() { return "Foydalanuvchi"; }
        @Override public List<String> responsibilities() {
            return List.of("Faqat ko'rish huquqi");
        }
    }
}