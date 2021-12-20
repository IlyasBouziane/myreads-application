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
    private UserBookTrackPrimaryKey bookTrackByUserId;

    @Column("rating")
    @CassandraType(type = Name.INT)
    private Integer bookRating;

    @CassandraType(type = Name.DATE)
    private LocalDate start_reading;

    @CassandraType(type = Name.DATE)
    private LocalDate complete_reading;

    @CassandraType(type = Name.TEXT)
    private String readingStatus;

    public UserBookTrack() {
    }

    public UserBookTrackPrimaryKey getBookTrackByUserId() {
        return bookTrackByUserId;
    }

    public void setBookTrackByUserId(UserBookTrackPrimaryKey bookTrackByUserId) {
        this.bookTrackByUserId = bookTrackByUserId;
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

    public LocalDate getComplete_reading() {
        return complete_reading;
    }

    public void setComplete_reading(LocalDate complete_reading) {
        this.complete_reading = complete_reading;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    
  
    
}
