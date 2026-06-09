package com.hotelos.authservice.domain;

import java.util.List;

/** Tozalovchi xodim. */
public class Cleaner extends Employee {
    public Cleaner(String username) { super(username); }

    @Override public String getRole() { return "CLEANER"; }
    @Override public String getTitle() { return "Tozalovchi"; }
    @Override public List<String> responsibilities() {
        return List.of("Xonalarni tozalash navbatini ko'rish", "Tozalash holatini yangilash");
    }
}