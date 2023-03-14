package com.github.youssefwadie.themoviedbgraphql.service.impl;

import com.github.youssefwadie.themoviedbgraphql.model.Movie;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResponse;
import com.github.youssefwadie.themoviedbgraphql.service.MovieService;
import com.github.youssefwadie.themoviedbgraphql.util.ThemoviedbUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private final WebClient webClient;

    @Override
    public Mono<SearchResponse> search(String keyword) {
        return webClient.get()
                .uri(uriBuilder -> buildSearchUri(uriBuilder, keyword))
                .retrieve()
                .bodyToMono(SearchResponse.class)
                .doOnNext(searchResponse -> {
                    var results = searchResponse.getResults();
                    results.forEach(searchResult -> {
                        var posterPath = ThemoviedbUtil.imageFullUrl(searchResult.getPosterPath());
                        var backdropPath = ThemoviedbUtil.imageFullUrl(searchResult.getBackdropPath());
                        searchResult.setPosterPath(posterPath);
                        searchResult.setBackdropPath(backdropPath);
                    });
                });
    }

    @Override
    public Mono<Movie> getMovieById(Integer id) {
        var uriVariables = Map.of("movie_id", id);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{movie_id}").build(uriVariables))
                .retrieve()
                .bodyToMono(Movie.class)
                .doOnNext(movie -> {
                    var posterPath = ThemoviedbUtil.imageFullUrl(movie.getPosterPath());
                    var backdropPath = ThemoviedbUtil.imageFullUrl(movie.getBackdropPath());
                    movie.setPosterPath(posterPath);
                    movie.setBackdropPath(backdropPath);
                });
    }

    private URI buildSearchUri(UriBuilder uriBuilder, String query) {
        return uriBuilder.path("/search/movie").queryParam("query", query).build();
    }
}
