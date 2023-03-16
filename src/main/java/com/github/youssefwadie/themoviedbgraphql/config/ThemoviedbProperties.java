package com.github.youssefwadie.themoviedbgraphql.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.themoviedb")
public class ThemoviedbProperties {

    public static final String API_KEY_QUERY_PARAM_NAME = "api_key";
    private String apiKey;
    private String apiBaseUrl;
}
