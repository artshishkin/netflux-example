package com.artarkatesoft.netfluxexample.controllers;

import com.artarkatesoft.netfluxexample.domain.Movie;
import com.artarkatesoft.netfluxexample.domain.MovieEvent;
import com.artarkatesoft.netfluxexample.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("{id}/events")
    Flux<MovieEvent> streamMovieEvents(@PathVariable("id") String id) {
        return movieService.events(id);
    }

    @GetMapping("{id}")
    Mono<Movie> getMovieById(@PathVariable("id") String id) {
        return movieService.getMovieById(id);
    }

    @GetMapping
    Flux<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

}
