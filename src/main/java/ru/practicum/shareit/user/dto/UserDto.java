package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;


@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(groups = {Create.class})
    private String name;
    @Email
    @NotNull(groups = {Create.class})
    private String email;

    public interface Create extends Default{}

    public interface Update extends Default{}
}

