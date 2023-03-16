package com.github.youssefwadie.themoviedbgraphql.model;

import lombok.Data;

import java.util.List;

@Data
public class Genres {
    private List<Genre> genres;
}
