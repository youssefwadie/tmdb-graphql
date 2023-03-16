package com.github.youssefwadie.themoviedbgraphql.clients.impl;

import com.github.youssefwadie.themoviedbgraphql.clients.MovieApiClient;
import com.github.youssefwadie.themoviedbgraphql.config.ThemoviedbProperties;
import com.github.youssefwadie.themoviedbgraphql.model.Genre;
import com.github.youssefwadie.themoviedbgraphql.model.Genres;
import com.github.youssefwadie.themoviedbgraphql.model.Movie;
import com.github.youssefwadie.themoviedbgraphql.model.SearchBag;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResponse;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResult;
import com.github.youssefwadie.themoviedbgraphql.util.ThemoviedbUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieApiClientImpl implements MovieApiClient {
    private final WebClient webClient;

    @Override
    public Mono<SearchResponse> search(String keyword) {
        return search(SearchBag.builder(keyword).build());
    }

    @Override
    public Mono<SearchResponse> search(SearchBag searchBag) {
        log.info("searching with {}", searchBag);
        return webClient.get()
                .uri(uriBuilder -> {
                    var builtUrl = buildSearchUrl(uriBuilder, searchBag);
                    var stripperUrl = removeApiKeyQuery(builtUrl);
                    log.info("getting {}", stripperUrl);
                    return builtUrl;
                })
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
                })
                .onErrorResume(this::onError);

    }


    @Override
    public Mono<Movie> getMovieById(Integer id) {
        log.info("fetching movie with id {}", id);
        return webClient.get()
                .uri(uriBuilder -> {
                    var uriVariables = Map.of("movie_id", id);
                    var builtUrl = uriBuilder.path("/movie/{movie_id}").build(uriVariables);
                    var stripperUrl = removeApiKeyQuery(builtUrl);
                    log.info("getting {}", stripperUrl);
                    return builtUrl;
                })
                .retrieve()
                .bodyToMono(Movie.class)
                .doOnNext(movie -> {
                    var posterPath = ThemoviedbUtil.imageFullUrl(movie.getPosterPath());
                    var backdropPath = ThemoviedbUtil.imageFullUrl(movie.getBackdropPath());
                    movie.setPosterPath(posterPath);
                    movie.setBackdropPath(backdropPath);
                })
                .onErrorResume(this::onError);
    }

    @Override
    public Mono<Genres> getAllGenres() {
        log.info("fetching all genres");
        return webClient.get()
                .uri(uriBuilder -> {
                    var builtUrl = uriBuilder.path("/genre/movie/list").build();
                    var stripperUrl = removeApiKeyQuery(builtUrl);
                    log.info("getting {}", stripperUrl);
                    return builtUrl;
                })
                .retrieve()
                .bodyToMono(Genres.class)
                .onErrorResume(this::onError);
    }
    public <T> Mono<T> onError(Throwable throwable) {
        log.warn("response error: {}", throwable.getMessage());
        return Mono.empty();
    }
    private URI buildSearchUrl(UriBuilder uriBuilder, SearchBag searchBag) {
        uriBuilder.path("/search/movie").queryParam("query", searchBag.getKeyword());

        if (Objects.nonNull(searchBag.getPage())) {
            uriBuilder.queryParam("page", searchBag.getPage());
        }
        if (Objects.nonNull(searchBag.getIncludeAdult())) {
            uriBuilder.queryParam("include_adult", searchBag.getIncludeAdult());
        }
        if (Objects.nonNull(searchBag.getRegion())) {
            uriBuilder.queryParam("region", searchBag.getRegion());
        }
        if (Objects.nonNull(searchBag.getYear())) {
            uriBuilder.queryParam("year", searchBag.getYear());
        }
        if (Objects.nonNull(searchBag.getPrimaryReleaseYear())) {
            uriBuilder.queryParam("primary_release_year", searchBag.getPrimaryReleaseYear());
        }
        return uriBuilder.build();
    }

    @Override
    public Mono<Map<SearchResult, List<Genre>>> mapGenres(List<SearchResult> searchResults) {
        log.info("mapping {} movies to genres", searchResults.size());
        return this.getAllGenres()
                .map(Genres::getGenres)
                .map(allGenres -> toMap(searchResults, allGenres));
    }

    private Map<SearchResult, List<Genre>> toMap(List<SearchResult> searchResults, List<Genre> genres) {
        return searchResults
                .stream()
                .collect(Collectors.toMap(
                        searchResult -> searchResult,
                        searchResult -> mapSearchResultToGenres(searchResult, genres)
                ));
    }

    private List<Genre> mapSearchResultToGenres(SearchResult searchResult, List<Genre> allGenres) {
        List<Integer> genreIds = searchResult.getGenreIds();
        return allGenres.stream().filter(genre -> genreIds.contains(genre.getId())).toList();
    }

    public URI removeApiKeyQuery(URI uri) {
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            List<NameValuePair> queryParameters = uriBuilder.getQueryParams();
            queryParameters.removeIf(queryParam -> queryParam.getName().equals(ThemoviedbProperties.API_KEY_QUERY_PARAM_NAME));
            uriBuilder.setParameters(queryParameters);
            return uriBuilder.build();
        } catch (URISyntaxException ignored) {
            return uri;
        }
    }

}
