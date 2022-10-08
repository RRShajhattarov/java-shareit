package ru.practicum.shareit.exception;

public class UserIdNotValidation extends RuntimeException{
    public UserIdNotValidation(String message) {
        super(message);
    }
}
