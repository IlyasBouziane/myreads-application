package com.myproject.myreadsapp.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class UserBookTrackPrimaryKey {
    @PrimaryKeyColumn(value = "book_id",ordinal = 0,type = PrimaryKeyType.PARTITIONED) 
    private String bookId;

    @PrimaryKeyColumn(value = "user_id",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private String userId;

    
    public UserBookTrackPrimaryKey() {
    }
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }


}