package com.example.goldClub.integration;

import org.junit.jupiter.api.BeforeEach;
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
    private String validToken;

    @BeforeEach
    public void setUp() {
        this.validToken = obtenerJwtToken();
    }

    private String obtenerJwtToken() {
        String loginUrl = BASE_URL + "/api/usuarios/login";
        String loginRequestJson = "{ \"email\": \"usuario@correo.com\", \"password\": \"password123\" }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(loginRequestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);
        return response.getBody(); // Retorna el token JWT de la respuesta
    }

    @Test
    public void testAutenticacionJWT() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + validToken);
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
