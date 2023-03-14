package com.github.youssefwadie.themoviedbgraphql;

import com.github.youssefwadie.themoviedbgraphql.config.ThemoviedbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ThemoviedbProperties.class)
public class ThemoviedbGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemoviedbGraphqlApplication.class, args);
    }

}
