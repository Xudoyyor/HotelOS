package com.hotelos.authservice.exceptions;

/** Login yoki parol noto'g'ri bo'lganda tashlanadi -> HTTP 401. */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) { super(message); }
}