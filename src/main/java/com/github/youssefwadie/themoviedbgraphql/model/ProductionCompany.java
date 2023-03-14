package com.github.youssefwadie.themoviedbgraphql.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductionCompany {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("logo_path")
    private String logoPath;

    @JsonProperty("origin_countries")
    private String originCountries;
}

