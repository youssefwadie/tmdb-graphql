package com.github.youssefwadie.themoviedbgraphql.service;


import com.github.youssefwadie.themoviedbgraphql.model.Movie;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResponse;
import reactor.core.publisher.Mono;

public interface MovieService {
    Mono<SearchResponse> search(String keyword);

    Mono<Movie> getMovieById(Integer id);
}
