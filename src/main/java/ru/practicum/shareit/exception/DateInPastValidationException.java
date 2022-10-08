package ru.practicum.shareit.exception;

public class DateInPastValidationException extends RuntimeException {
    public DateInPastValidationException(String message) {super(message);}
}
