package com.hotelos.roomservice.exceptions;

/** So'ralgan resurs topilmaganda tashlanadi -> HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}