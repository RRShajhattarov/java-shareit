package ru.practicum.shareit.user;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
