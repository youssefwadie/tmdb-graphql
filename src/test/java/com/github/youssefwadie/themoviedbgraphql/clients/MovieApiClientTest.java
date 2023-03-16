package com.github.youssefwadie.themoviedbgraphql.clients;

import com.github.youssefwadie.themoviedbgraphql.clients.impl.MovieApiClientImpl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Nothing to test, really ... but to be safe
 */
@ExtendWith(MockitoExtension.class)
class MovieApiClientTest {

    private MovieApiClient underTest;
    private MockWebServer mockWebServer;


    @BeforeEach
    public void init() {
        mockWebServer = new MockWebServer();
        var webClient = WebClient.create("http://localhost:%d".formatted(mockWebServer.getPort()));
        underTest = new MovieApiClientImpl(webClient);
    }

    @Test
    void search() throws InterruptedException {
        var keyword = "Harry Potter";
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        underTest.search(keyword).block();
        var recordedRequest = mockWebServer.takeRequest();

        var expectedRequestPath = new DefaultUriBuilderFactory().builder()
                .path("/search/movie").queryParam("query", keyword)
                .build()
                .toString();

        assertThat(recordedRequest.getPath()).isEqualTo(expectedRequestPath);
    }

    @Test
    void getMovieById() throws InterruptedException {
        var id = 5;
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        underTest.getMovieById(id).block();
        var recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/movie/" + id);
    }
}