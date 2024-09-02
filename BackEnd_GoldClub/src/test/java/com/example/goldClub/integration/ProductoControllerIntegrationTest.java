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

import com.example.goldClub.GoldClubApplication;
import com.example.goldClub.models.Producto;
import com.example.goldClub.repository.ProductoRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GoldClubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductoControllerIntegrationTest {

    private final String baseUrl = "https://goldclub-production.up.railway.app"; // Cambia la URL a la de tu backend en la nube
    private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXVAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjUyNTY2MjAsImV4cCI6MTcyNTI5MjYyMH0.E_9cugwxXyuExOh1je4-bi-NBbaGEtQfZMxRcSLInoo"; // Token JWT

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    public void setUp() {
        productoRepository.deleteAll();
    }

    @Test
    public void testListarProductos() {
        Producto producto = new Producto("Producto 1", "Descripción 1", new BigDecimal("10.00"));
        productoRepository.save(producto);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Producto[]> response = restTemplate.exchange(
            baseUrl + "/api/productos",
            HttpMethod.GET,
            entity,
            Producto[].class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getNombre()).isEqualTo("Producto 1");
    }


    @Test
    public void testEliminarProducto() {
        Producto producto = new Producto("Producto 3", "Descripción 3", new BigDecimal("30.00"));
        Producto savedProducto = productoRepository.save(producto);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
            baseUrl + "/api/productos/" + savedProducto.getId(),
            HttpMethod.DELETE,
            entity,
            Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productoRepository.findById(savedProducto.getId())).isEmpty();
    }
}

