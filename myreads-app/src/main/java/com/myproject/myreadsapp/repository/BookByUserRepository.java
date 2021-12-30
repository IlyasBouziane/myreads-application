package com.myproject.myreadsapp.repository;

import com.myproject.myreadsapp.model.BookByUser;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BookByUserRepository extends CassandraRepository<BookByUser,String> {
    Slice<BookByUser> findAllById(String userId, Pageable pageable);
}
