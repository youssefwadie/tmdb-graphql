package com.github.youssefwadie.themoviedbgraphql.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class SearchResult {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("adult")
    private boolean adult;
    // to be used in batch
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    private List<Genre> genres;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("popularity")
    private BigDecimal popularity;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("release_date")
    private LocalDate releaseDate;


    @JsonProperty("video")
    private boolean video;

    @JsonProperty("vote_average")
    private BigDecimal voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;
}
