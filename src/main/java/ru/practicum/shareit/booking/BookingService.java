package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingLongDto;
import ru.practicum.shareit.booking.dto.BookingWithDateDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.booking.validation.DateValidator;
import ru.practicum.shareit.enums.BookingStateEnum;
import ru.practicum.shareit.enums.BookingStatus;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.DateInPastValidationException;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        if (!item.getAvailable()) {
            throw new NotAvailableException("Item doesn't available.");
        }
        book.setItem(item);
        book.setBooker(user);
        book.setStatus(BookingStatus.WAITING);
    }

    public BookingLongDto changeStatus(Long userId, Long bookingId, Boolean approved) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User doesn't exists"));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking doesn't exists"));

        if (!userId.equals(booking.getItem().getOwner().getId())) {
            throw new NotFoundException("You are not the owner of the item");
        }
        if(approved && booking.getStatus().toString().equals("APPROVED")) {
            throw new BadRequestException("The status " + "APPROVED" + " is already set");
        }
        if(approved) booking.setStatus(BookingStatus.APPROVED);
        else booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return BookingMapper.toBookingLongDto(booking);
    }

    public BookingWithDateDto getBooking(Long userId, Long bookingId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User doesn't exists"));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking doesn't exists"));
        if(!(userId.equals(booking.getItem().getOwner().getId()) || userId.equals(booking.getBooker().getId()))) {
            throw new NotFoundException("You are not the owner or booker");
        }
        return BookingMapper.toBookingWithDateDto(booking);
    }

    public List<BookingWithDateDto> getAllBookings(String state, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User doesn't exists"));
        List<Booking> bookings = bookingRepository.getBookingsOfUser(userId);
        checkState(state);
        return getFromState(state, bookings).stream()
                .map(BookingMapper::toBookingWithDateDto)
                .collect(Collectors.toList());
    }

    private void checkState(String state) {
        List<String> stateList = new ArrayList<>();
        for (BookingStateEnum s: new ArrayList<>(Arrays.asList(BookingStateEnum.values()))) {
            stateList.add(s.toString());
        }
        if (!stateList.contains(state)) {
            throw new BadRequestException("Unknown state: " + state);
        }
    }
    public List<BookingWithDateDto> getOwnerBookings(String state, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User doesn't exists"));
        List<Booking> bookings = bookingRepository.findAll().stream()
                .filter(s -> Objects.equals(s.getItem().getOwner().getId(), userId))
                .collect(Collectors.toList());
        checkState(state);
        return getFromState(state, bookings).stream()
                .map(BookingMapper::toBookingWithDateDto)
                .collect(Collectors.toList());
    }
    private List<Booking> getFromState(String state, List<Booking> bookings) {
        Comparator<Booking> comparator = (o1, o2) -> o2.getStart().compareTo(o1.getStart());

        if (state != null) {

            switch (state) {
                case "CURRENT": {
                    return bookings.stream()
                            .filter(s -> s.getStart().isBefore(LocalDateTime.now()) &&
                                    s.getEnd().isAfter(LocalDateTime.now()))
                            .sorted(comparator)
                            .collect(Collectors.toList());
                }
                case "PAST": {
                    return bookings.stream()
                            .filter(s -> s.getEnd().isBefore(LocalDateTime.now()))
                            .sorted(comparator)
                            .collect(Collectors.toList());
                }
                case "FUTURE": {
                    return bookings.stream()
                            .filter(s -> s.getStart().isAfter(LocalDateTime.now()))
                            .sorted(comparator)
                            .collect(Collectors.toList());
                }
                case "WAITING": {
                    return bookings.stream()
                            .filter(s -> s.getStatus().equals(BookingStatus.WAITING))
                            .sorted(comparator)
                            .collect(Collectors.toList());
                }
                case "REJECTED": {
                    return bookings.stream()
                            .filter(s -> s.getStatus().equals(BookingStatus.REJECTED))
                            .sorted(comparator)
                            .collect(Collectors.toList());
                }
            }
        }
        return bookings.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
