package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;

/**
 * TODO Sprint add-controllers.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ItemDto {
    private String name;
    private String description;
    private boolean available;
    private int request;
}
