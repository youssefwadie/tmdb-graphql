package com.github.youssefwadie.themoviedbgraphql.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpokenLanguage {
    @JsonProperty("iso_639_1")
    private String iso_639_1;
    @JsonProperty("name")
    private String name;
}
