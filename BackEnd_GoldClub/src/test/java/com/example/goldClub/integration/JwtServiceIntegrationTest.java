package com.example.goldClub.integration;

import com.example.goldClub.models.Usuario;
import com.example.goldClub.security.JwtService;
import com.example.goldClub.service.UsuarioService;

import io.jsonwebtoken.MalformedJwtException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtServiceIntegrationTest {

    @Autowired
    private JwtService jwtService;

    private static final String BASE_URL = "https://goldclub-production.up.railway.app";
    private static String VALID_TOKEN;

    @BeforeEach
    void setUp() {
        // Obtener un token JWT válido del backend de producción
        VALID_TOKEN = obtenerTokenJwtValido("usuario@correo.com", "password123");
        System.out.println("Token JWT obtenido: " + VALID_TOKEN); // Imprimir el token para verificar
    }



    private String obtenerTokenJwtValido(String email, String password) {
        RestTemplate restTemplate = new RestTemplate();
        String loginUrl = BASE_URL + "/api/usuarios/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        // Cuerpo de la solicitud de login
        String loginRequestJson = String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password);
        HttpEntity<String> request = new HttpEntity<>(loginRequestJson, headers);

        // Realizar la solicitud de login
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, request, String.class);

        // Verificar que la respuesta de login sea exitosa y obtener el token
        assertTrue(response.getStatusCode().is2xxSuccessful(), "La solicitud de login debe ser exitosa");
        return response.getBody();
    }

    @Test
    void testGenerarToken() {
        // Simular un usuario
        String username = "usuario@correo.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(username);
        usuario.setPassword("password"); // Asegúrate de establecer una contraseña válida si es necesario para el JWT
        usuario.setCodigoEmpleado(123); // O el valor adecuado para el código de empleado
        
        // Generar token JWT
        String token = jwtService.generateToken(usuario);

        // Validar el token generado
        assertDoesNotThrow(() -> {
            assertTrue(jwtService.isTokenValid(token), "El token JWT debería ser válido");
        });
    }

    /*@Test
    void testValidarToken() {
        // Validar el token JWT
        boolean isValid = jwtService.isTokenValid(VALID_TOKEN);
        assertTrue(isValid, "El token JWT debería ser válido");
    }*/




    @Test
    void testObtenerUsuarioDesdeToken() {
        // Simular un usuario
        String username = "usuario@correo.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(username);
        usuario.setPassword("password");
        usuario.setCodigoEmpleado(123);

        // Generar un token válido
        String token = jwtService.generateToken(usuario);

        // Obtener el nombre de usuario desde un token válido
        String extractedUsername = jwtService.extractUsernameFromToken(token);

        // Verificar que el nombre de usuario coincida
        assertTrue(extractedUsername.equals(username), "El nombre de usuario obtenido desde el token debería ser el correcto");
    }


    @Test
    void testObtenerUsuarioDesdeTokenInvalido() {
        // Proporcionar un token inválido
        String invalidToken = "invalid.token.value";

        // Verificar que la obtención del nombre de usuario desde un token inválido lance una excepción
        assertThrows(MalformedJwtException.class, () -> {
            jwtService.extractUsernameFromToken(invalidToken);
        });
    }
    

}
