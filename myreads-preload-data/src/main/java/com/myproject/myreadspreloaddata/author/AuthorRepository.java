package com.myproject.myreadspreloaddata.author;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CassandraRepository<AuthorEntity,String> {
    public AuthorEntity findByAuthorId(String authorId);
}
