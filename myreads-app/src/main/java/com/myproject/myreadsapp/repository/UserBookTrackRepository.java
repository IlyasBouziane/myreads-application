package com.myproject.myreadsapp.repository;

import com.myproject.myreadsapp.model.UserBookTrack;
import com.myproject.myreadsapp.model.UserBookTrackPrimaryKey;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserBookTrackRepository extends CassandraRepository<UserBookTrack,UserBookTrackPrimaryKey>  {
    
}
