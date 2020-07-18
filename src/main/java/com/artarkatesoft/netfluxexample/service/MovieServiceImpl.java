package com.artarkatesoft.netfluxexample.service;

import com.artarkatesoft.netfluxexample.domain.Movie;
import com.artarkatesoft.netfluxexample.domain.MovieEvent;
import com.artarkatesoft.netfluxexample.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Override
    public Flux<MovieEvent> events(String movieId) {
        return Flux.<MovieEvent>generate(movieEventSynchronousSink -> {
            movieEventSynchronousSink.next(new MovieEvent(movieId, new Date()));
        }).delayElements(Duration.ofSeconds(1));

    }

    @Override
    public Mono<Movie> getMovieById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Movie> getAllMovies() {
        return repository.findAll();
    }
}
