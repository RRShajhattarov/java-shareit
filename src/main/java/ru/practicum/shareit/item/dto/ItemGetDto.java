package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ItemGetDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private LocalDateTime startBooking;
    private LocalDateTime endBooking;
}
