package com.hotelos.authservice.exceptions;

/** Foydalanuvchi nomi band bo'lganda tashlanadi -> HTTP 409. */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) { super(message); }
}