package com.myproject.myreadspreloaddata.book;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table(value = "book_by_id")
public class BookEntity {

    @Id @PrimaryKeyColumn(value = "book_id",ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String bookId;

    @Column(value = "book_name")
    private String bookName;

    @Column(value = "book_description")
    private String bookDescription;

    @Column(value = "published_date")
    private LocalDate publishedDate;

    @Column(value = "covers_ids")
    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> coversIds;

    @Column(value = "authors_ids")
    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> authorsIds;

    @Column(value = "authors_names")
    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> authorsNames;
    
    public BookEntity() {
    }
    
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookDescription() {
        return bookDescription;
    }
    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
    public LocalDate getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    public List<String> getCoversIds() {
        return coversIds;
    }
    public void setCoversIds(List<String> coversIds) {
        this.coversIds = coversIds;
    }
    public List<String> getAuthorsIds() {
        return authorsIds;
    }
    public void setAuthorsIds(List<String> authorsIds) {
        this.authorsIds = authorsIds;
    }
    public List<String> getAuthorsNames() {
        return authorsNames;
    }
    public void setAuthorsNames(List<String> authorsNames) {
        this.authorsNames = authorsNames;
    }

    
    
}
