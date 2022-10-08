package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.booking.validation.DateValidator;
import ru.practicum.shareit.enums.BookingStatus;
import ru.practicum.shareit.exception.DateInPastValidationException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Resource
    BookingRepository bookingRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DateValidator dateValidator;


    public BookingDto addBooking(BookingDto bookingDto, Long userId) {
        if (!dateValidator.validateDtoDate(bookingDto)) {
            throw new DateInPastValidationException("Check your date");
        }
        Booking book = BookingMapper.toBooking(bookingDto);
        fillBooking(book, bookingDto, userId);
        return BookingMapper.toBookingDto(bookingRepository.save(book));
    }

    private void fillBooking(Booking book, BookingDto bookingDto, Long userId) {
        Optional<Item> optItem = itemRepository.findById(bookingDto.getItemId());
        Item item = optItem.orElseThrow(() -> new NotFoundException("Item doesn't exists"));
        Optional<User> optUser = userRepository.findById(userId);
        User user = optUser.orElseThrow(() -> new NotFoundException("User doesn't exists"));
        book.setItem(item);
        book.setBooker(user);
        book.setStatus(BookingStatus.WAITING);
    }
}
