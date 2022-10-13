package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.enums.BookingStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingLongDto {
    private Long id;
    private BookingStatus status;
    private Long bookerId;
    private Long itemId;
    private String itemName;

}
