package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    String error;
    String description;

    public ErrorResponse(String error) {this.error = error;}

}
