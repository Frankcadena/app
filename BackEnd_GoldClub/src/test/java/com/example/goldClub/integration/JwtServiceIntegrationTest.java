package com.example.goldClub.integration;

import com.example.goldClub.models.Usuario;
import com.example.goldClub.security.JwtService;
import com.example.goldClub.service.UsuarioService;

import io.jsonwebtoken.MalformedJwtException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtServiceIntegrationTest {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvQGNvcnJlby5jb20iLCJpYXQiOjE3MjUyNTg5NzMsImV4cCI6MTcyNTI5NDk3M30.8Q18OriZvClvlfTtBgt-wOh2qFhH72DUJ44oEhgx2aw";

    @BeforeEach
    void setUp() {
        // Preparar cualquier configuración o datos necesarios antes de cada prueba
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

    @Test
    void testValidarToken() {
        // Simular un usuario
        String username = "usuario@correo.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(username);
        usuario.setPassword("password");
        usuario.setCodigoEmpleado(123);

        // Generar un token válido
        String token = jwtService.generateToken(usuario);

        // Validar el token JWT
        boolean isValid = jwtService.isTokenValid(token);
        assertTrue(isValid, "El token JWT debería ser válido");
    }



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
