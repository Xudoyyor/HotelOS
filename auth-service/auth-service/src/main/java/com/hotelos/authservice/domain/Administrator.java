package com.hotelos.authservice.domain;

import java.util.List;

/** Administrator - to'liq huquqli xodim. */
public class Administrator extends Employee {
    public Administrator(String username) { super(username); }

    @Override public String getRole() { return "ADMIN"; }
    @Override public String getTitle() { return "Administrator"; }
    @Override public List<String> responsibilities() {
        return List.of("Barcha modullarni boshqarish", "Xodimlarni boshqarish", "Hisobotlar");
    }
}