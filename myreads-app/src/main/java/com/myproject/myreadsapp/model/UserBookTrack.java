package com.myproject.myreadsapp.model;

import java.time.LocalDate;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table(value = "book_tracked_by_user")
public class UserBookTrack {
    @PrimaryKey
    private UserBookTrack bookTrackByUser;

    @Column("rating")
    @CassandraType(type = Name.INT)
    private Integer bookRating;

    @CassandraType(type = Name.DATE)
    private LocalDate start_reading;

    @CassandraType(type = Name.DATE)
    private LocalDate end_reading;

    public UserBookTrack() {
    }

    public UserBookTrack getBookTrackByUser() {
        return bookTrackByUser;
    }

    public void setBookTrackByUser(UserBookTrack bookTrackByUser) {
        this.bookTrackByUser = bookTrackByUser;
    }

    public Integer getBookRating() {
        return bookRating;
    }

    public void setBookRating(Integer bookRating) {
        this.bookRating = bookRating;
    }

    public LocalDate getStart_reading() {
        return start_reading;
    }

    public void setStart_reading(LocalDate start_reading) {
        this.start_reading = start_reading;
    }

    public LocalDate getEnd_reading() {
        return end_reading;
    }

    public void setEnd_reading(LocalDate end_reading) {
        this.end_reading = end_reading;
    }

    
}
