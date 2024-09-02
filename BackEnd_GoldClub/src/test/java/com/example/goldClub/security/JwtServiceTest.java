package com.example.goldClub.security;

import com.example.goldClub.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//Clase de prueba para el servicio de JWT.
public class JwtServiceTest {

 @InjectMocks
 private JwtService jwtService; // Inyecta el servicio de JWT en el contexto de prueba.

 @Mock
 private Claims claims; // Simula los claims de JWT.

 private String secret = "thisisaverylongsecretkeyforjwtwhichneedstobesecure"; // Clave secreta para pruebas.

 @BeforeEach
 public void setUp() {
     MockitoAnnotations.openMocks(this); // Inicializa los mocks.
     jwtService.setSecret(secret); // Configura la clave secreta en el servicio.
 }

 // Prueba para generar un token JWT.
 @Test
 public void testGenerateToken() {
     Usuario usuario = new Usuario(); // Crea un usuario de prueba.
     usuario.setEmail("test@example.com");

     // Genera un token usando el servicio.
     String token = jwtService.generateToken(usuario);

     // Verifica que el token generado contenga el nombre de usuario correcto.
     assertEquals("test@example.com", jwtService.extractUsername(token));
 }

 // Prueba para verificar si un token es válido.
 @Test
 public void testIsTokenValid() {
     Usuario usuario = new Usuario(); // Crea un usuario de prueba.
     usuario.setEmail("test@example.com");
     String token = jwtService.generateToken(usuario); // Genera un token válido.

     // Usa el método real para verificar la validez del token.
     boolean isValid = jwtService.isTokenValid(token);

     // Verifica que el token sea válido.
     assertEquals(true, isValid);
 }

 // Prueba para extraer un claim de un token JWT.
 @Test
 public void testExtractClaim() {
     Usuario usuario = new Usuario(); // Crea un usuario de prueba.
     usuario.setEmail("test@example.com");
     String token = jwtService.generateToken(usuario); // Genera un token de prueba.

     // Extrae el claim usando el servicio.
     String email = jwtService.extractClaim(token, Claims::getSubject);

     // Verifica que el claim extraído sea correcto.
     assertEquals("test@example.com", email);
 }
}
