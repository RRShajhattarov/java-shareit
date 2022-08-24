package ru.practicum.shareit.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {
    private int id;
    private String name;
    @NotNull
    @NotBlank
    @Email(message = "Email недействительный")
    private String email;

}
