package com.github.youssefwadie.themoviedbgraphql.ui;

import com.github.youssefwadie.themoviedbgraphql.clients.MovieApiClient;
import com.github.youssefwadie.themoviedbgraphql.model.Genre;
import com.github.youssefwadie.themoviedbgraphql.model.Movie;
import com.github.youssefwadie.themoviedbgraphql.model.SearchBag;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResponse;
import com.github.youssefwadie.themoviedbgraphql.model.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MovieController {
    private final MovieApiClient movieApiClient;

    @QueryMapping
    public Mono<SearchResponse> movies(@Argument("keyword") String keyword,
                                       @Argument("page") Optional<Integer> page,
                                       @Argument("includeAdult") Optional<Boolean> includeAdult,
                                       @Argument("region") Optional<String> region,
                                       @Argument("year") Optional<Integer> year,
                                       @Argument("primaryReleaseYear") Optional<Integer> primaryReleaseYear) {
        final var searchBagBuilder = SearchBag.builder(keyword);

        page.ifPresent(searchBagBuilder::page);
        includeAdult.ifPresent(searchBagBuilder::includeAdult);
        region.ifPresent(searchBagBuilder::region);
        year.ifPresent(searchBagBuilder::year);
        primaryReleaseYear.ifPresent(searchBagBuilder::primaryReleaseYear);

        final var searchBag = searchBagBuilder.build();

        return movieApiClient.search(searchBag);
    }

    @QueryMapping
    public Mono<Movie> movieById(@Argument("id") Integer id) {
        return movieApiClient.getMovieById(id);
    }

    @BatchMapping(typeName = "SearchResult", field = "genres")
    public Mono<Map<SearchResult, List<Genre>>> genres(List<SearchResult> searchResults) {
        log.info("fetching genres for searchResults of size {}", searchResults.size());
        return movieApiClient.mapGenres(searchResults);

    }

}
