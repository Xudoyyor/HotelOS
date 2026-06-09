package com.hotelos.authservice.domain;

import java.util.List;

/** Texnik xodim. */
public class Technician extends Employee {
    public Technician(String username) { super(username); }

    @Override public String getRole() { return "MAINTENANCE"; }
    @Override public String getTitle() { return "Texnik xodim"; }
    @Override public List<String> responsibilities() {
        return List.of("Nosozliklarni qabul qilish", "Ustuvor vazifalarni bajarish");
    }
}