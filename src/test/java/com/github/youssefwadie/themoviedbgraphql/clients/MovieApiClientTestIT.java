package com.github.youssefwadie.themoviedbgraphql.clients;

import com.github.youssefwadie.themoviedbgraphql.model.Genres;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class MovieApiClientTestIT {
    @Autowired
    private MovieApiClient underTest;

    @Test
    void search() {

    }

    @Test
    void getMovieById() {

    }

    @Test
    void getAllGenres() {
        Mono<Genres> genresMono = underTest.getAllGenres();
        StepVerifier.create(genresMono)
                .consumeNextWith(genres -> {
                    assertThat(genres).isNotNull();

                    var genresList = genres.getGenres();
                    assertThat(genresList).isNotNull();

                    for (var genre : genresList) {
                        assertThat(genre.getId()).isNotNull();
                        assertThat(genre.getName()).isNotNull();
                    }
                })
                .verifyComplete();
    }
}