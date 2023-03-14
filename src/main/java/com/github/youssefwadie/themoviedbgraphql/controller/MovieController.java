package com.github.youssefwadie.themoviedbgraphql.controller;

import com.github.youssefwadie.themoviedbgraphql.model.Movie;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResponse;
import com.github.youssefwadie.themoviedbgraphql.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
public class MovieController {
    private final MovieService movieService;

    @QueryMapping
    public Mono<SearchResponse> search(@Argument("keyword") String keyword) {
        return movieService.search(keyword);
    }

    @QueryMapping
    public Mono<Movie> movieById(@Argument("id") Integer id) {
        return movieService.getMovieById(id);
    }
}
