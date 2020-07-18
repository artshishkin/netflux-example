package com.artarkatesoft.netfluxexample.service;

import com.artarkatesoft.netfluxexample.domain.Movie;
import com.artarkatesoft.netfluxexample.domain.MovieEvent;
import com.artarkatesoft.netfluxexample.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Override
    public Flux<MovieEvent> events(String movieId) {
        return Flux.<MovieEvent>generate(movieEventSynchronousSink -> {
            MovieEvent movieEvent = new MovieEvent(movieId, new Date());
            movieEventSynchronousSink.next(movieEvent);
            log.debug("Streaming {}",movieEvent);
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
