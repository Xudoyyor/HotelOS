package com.hotelos.authservice.domain;

import java.util.List;

/** Qabul xodimi. */
public class Receptionist extends Employee {
    public Receptionist(String username) { super(username); }

    @Override public String getRole() { return "RECEPTION"; }
    @Override public String getTitle() { return "Qabul xodimi"; }
    @Override public List<String> responsibilities() {
        return List.of("Mehmonlarni ro'yxatga olish", "Check-in / Check-out", "Hisob-kitob");
    }
}