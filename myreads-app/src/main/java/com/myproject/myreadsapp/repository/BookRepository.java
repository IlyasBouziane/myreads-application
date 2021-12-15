package com.myproject.myreadsapp.repository;

import com.myproject.myreadsapp.model.Book;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CassandraRepository<Book,String> {
    
}
