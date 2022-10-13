package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.enums.BookingStatus;
import ru.practicum.shareit.item.model.Comment;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Transactional
    @Modifying
    @Query("update Booking b set b.status = ?2 where b.id = ?1")
    void setBookingStatus(Long bookingId, BookingStatus status);

    @Query("select c from Comment c where c.item.id = ?1")
    List<Comment> getCommentByItemId(Long itemId);
}
