package com.myproject.myreadspreloaddata.author;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table(value = "author_by_id")
public class AuthorEntity {
    @Id @PrimaryKeyColumn(ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private String authorId;

    @Column
    @CassandraType(type = Name.TEXT)
    private String authorName;
    
    @Column
    @CassandraType(type = Name.TEXT)
    private String authorPersonalName;


    public AuthorEntity() {
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorPersonalName() {
        return authorPersonalName;
    }

    public void setAuthorPersonalName(String authorPersonalName) {
        this.authorPersonalName = authorPersonalName;
    }

    


}
