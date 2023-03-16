package com.github.youssefwadie.themoviedbgraphql.clients;


import com.github.youssefwadie.themoviedbgraphql.model.Genre;
import com.github.youssefwadie.themoviedbgraphql.model.Genres;
import com.github.youssefwadie.themoviedbgraphql.model.Movie;
import com.github.youssefwadie.themoviedbgraphql.model.SearchBag;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResponse;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResult;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface MovieApiClient {
    Mono<SearchResponse> search(String keyword);

    Mono<SearchResponse> search(SearchBag searchBag);

    Mono<Movie> getMovieById(Integer id);

    Mono<Genres> getAllGenres();

    Mono<Map<SearchResult, List<Genre>>> mapGenres(List<SearchResult> searchResults);
}
