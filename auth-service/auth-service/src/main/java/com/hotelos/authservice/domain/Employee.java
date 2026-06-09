package com.hotelos.authservice.domain;

import java.util.List;

/**
 * OOP - ABSTRAKSIYA + INKAPSULYATSIYA.
 * Tizimdagi har qanday xodimning umumiy shartnomasi (abstract base class).
 * Aniq rol va mas'uliyatlar avlod (subclass) sinflarda belgilanadi (POLIMORFIZM).
 */
public abstract class Employee {

    protected final String username;

    protected Employee(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /** Tizimdagi rol kodi (Role enum nomi bilan mos). */
    public abstract String getRole();

    /** Lavozimning o'qishbop nomi. */
    public abstract String getTitle();

    /** Ushbu rolga biriktirilgan asosiy mas'uliyatlar. */
    public abstract List<String> responsibilities();

    /**
     * Polimorf metod: har bir avlod o'z mas'uliyatlari asosida turlicha natija beradi.
     */
    public String describeAccess() {
        return getTitle() + " (" + username + ") -> ruxsatlar: " + String.join(", ", responsibilities());
    }
}