package com.myproject.myreadsapp.repository;

import com.myproject.myreadsapp.model.BookByUser;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface BookByUserRepository extends CassandraRepository<BookByUser,String> {
    
}
