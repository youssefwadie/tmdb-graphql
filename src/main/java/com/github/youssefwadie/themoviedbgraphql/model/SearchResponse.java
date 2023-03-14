package com.github.youssefwadie.themoviedbgraphql.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("results")
    List<SearchResult> results;
    @JsonProperty(value = "total_pages")
    private Integer totalPages;
    @JsonProperty(value = "total_results")
    private Integer totalResults;
}
