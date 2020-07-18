package com.artarkatesoft.netfluxexample.repositories;

import com.artarkatesoft.netfluxexample.domain.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieRepository extends ReactiveMongoRepository<Movie,String> {
}
