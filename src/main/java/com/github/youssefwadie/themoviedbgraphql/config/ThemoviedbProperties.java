package com.github.youssefwadie.themoviedbgraphql.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "app.themoviedb")
public class ThemoviedbProperties {

    public static final String API_KEY_QUERY_PARAM_NAME = "api_key";

    private final String apiKey;
    private final String apiBaseUrl;
    @ConstructorBinding
    public ThemoviedbProperties(String apiKey, String apiBaseUrl) {
        this.apiKey = apiKey;
        this.apiBaseUrl = apiBaseUrl;
    }
}
