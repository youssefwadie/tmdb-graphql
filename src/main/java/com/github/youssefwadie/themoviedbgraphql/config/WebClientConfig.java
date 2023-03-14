package com.github.youssefwadie.themoviedbgraphql.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import static com.github.youssefwadie.themoviedbgraphql.config.ThemoviedbProperties.API_KEY_QUERY_PARAM_NAME;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebClientConfig {
    private final ThemoviedbProperties properties;

    @Bean
    public WebClient webClient() {
        var authorizedUriBuilderFactory = new DefaultUriBuilderFactory(authorizedUriBuilderFactory());
        final var objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        var strategies = ExchangeStrategies.builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    var encoder = new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON);
                    var decoder = new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON);
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(encoder);
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(decoder);
                }).build();

        return WebClient.builder()
                .baseUrl(properties.getApiBaseUrl())
                .uriBuilderFactory(authorizedUriBuilderFactory)
                .exchangeStrategies(strategies)
                .build();
    }

    private UriComponentsBuilder authorizedUriBuilderFactory() {
        return UriComponentsBuilder.fromHttpUrl(properties.getApiBaseUrl())
                .queryParam(API_KEY_QUERY_PARAM_NAME, properties.getApiKey());
    }

}
