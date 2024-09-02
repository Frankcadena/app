package com.example.goldClub.security;

import com.example.goldClub.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.mockito.Mockito.*;

//Clase de prueba para el filtro de autenticación JWT.
public class JwtAuthenticationFilterTest {

 @Mock
 private JwtService jwtService; // Simula el servicio de JWT.

 @Mock
 private UsuarioService usuarioService; // Simula el servicio de usuarios.

 @InjectMocks
 private JwtAuthenticationFilter jwtAuthenticationFilter; // Inyecta el filtro de autenticación JWT en el contexto de prueba.

 public JwtAuthenticationFilterTest() {
     MockitoAnnotations.openMocks(this); // Inicializa los mocks.
 }

 // Prueba para el método doFilterInternal del filtro JWT.
 @Test
 public void testDoFilterInternal() throws Exception {
     HttpServletRequest request = mock(HttpServletRequest.class); // Simula una solicitud HTTP.
     HttpServletResponse response = mock(HttpServletResponse.class); // Simula una respuesta HTTP.
     FilterChain chain = mock(FilterChain.class); // Simula una cadena de filtros.

     String token = "testToken"; // Token de prueba.
     String username = "test@example.com"; // Nombre de usuario de prueba.
     UserDetails userDetails = mock(UserDetails.class); // Simula los detalles del usuario.

     // Simula el comportamiento del servicio de JWT.
     when(jwtService.extractToken(request)).thenReturn(token);
     when(jwtService.isTokenValid(token)).thenReturn(true);
     when(jwtService.extractUsername(token)).thenReturn(username);
     when(usuarioService.loadUserByUsername(username)).thenReturn(userDetails);

     // Llama al método del filtro.
     jwtAuthenticationFilter.doFilterInternal(request, response, chain);

     // Verifica que la cadena de filtros continúe el procesamiento.
     verify(chain).doFilter(request, response);
 }
}
