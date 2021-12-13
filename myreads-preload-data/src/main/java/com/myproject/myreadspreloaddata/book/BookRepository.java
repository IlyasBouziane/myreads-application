package com.myproject.myreadspreloaddata.book;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CassandraRepository<BookEntity,String> {
    
}
