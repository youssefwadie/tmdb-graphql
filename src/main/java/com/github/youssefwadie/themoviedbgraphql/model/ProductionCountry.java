package com.github.youssefwadie.themoviedbgraphql.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductionCountry {
    @JsonProperty("iso_3166_1")
    private String iso_3166_1;
    @JsonProperty("name")
    private String name;
}
