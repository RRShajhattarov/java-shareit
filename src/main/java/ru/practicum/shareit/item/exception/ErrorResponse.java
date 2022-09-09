package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    String error;
    String description;

    public ErrorResponse(String error) {this.error = error;}

}
