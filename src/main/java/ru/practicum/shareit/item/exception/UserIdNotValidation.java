package ru.practicum.shareit.item.exception;

public class UserIdNotValidation extends RuntimeException{
    public UserIdNotValidation(String message) {
        super(message);
    }
}
