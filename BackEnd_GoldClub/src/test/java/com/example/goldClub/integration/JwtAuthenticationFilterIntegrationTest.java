package com.example.goldClub.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthenticationFilterIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "https://goldclub-production.up.railway.app";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvQGNvcnJlby5jb20iLCJpYXQiOjE3MjUyNTg5NzMsImV4cCI6MTcyNTI5NDk3M30.8Q18OriZvClvlfTtBgt-wOh2qFhH72DUJ44oEhgx2aw";

    @Test
    public void testAutenticacionJWT() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + VALID_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            createURL("/api/productos"),
            HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testAccesoDenegadoSinJWT() {
        ResponseEntity<String> response = restTemplate.getForEntity(createURL("/api/productos"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private String createURL(String uri) {
        return BASE_URL + uri;
    }
}
