package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long > {

    @Query("select b from Booking b where b.booker.id = ?1")
    List<Booking> getBookingsOfUser(Long bookerId);

    @Query("select b from Booking b where b.item.id = ?1 and b.booker.id = ?2")
    Optional<List<Booking>> getBookingByItemIdAndUserId(Long itemId, Long userId);

}
