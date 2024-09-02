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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.goldClub.GoldClubApplication;
import com.example.goldClub.models.Usuario;
import com.example.goldClub.repository.UsuarioRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GoldClubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    public void testRegistrarUsuario() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail("nuevo@usuario.com");
        nuevoUsuario.setCodigoEmpleado(111);
        nuevoUsuario.setPassword("password123");

        HttpEntity<Usuario> request = new HttpEntity<>(nuevoUsuario);

        ResponseEntity<Usuario> response = restTemplate.postForEntity(createURL("/api/usuarios/registro"), request, Usuario.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(usuarioRepository.findByEmail("nuevo@usuario.com")).isPresent();
    }

    @Test
    public void testLoginUsuario() {
        // Registra un usuario
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@correo.com");
        usuario.setCodigoEmpleado(111); // Usa un código de empleado válido
        usuario.setPassword(passwordEncoder.encode("password123")); // Encripta la contraseña
        usuarioRepository.save(usuario);

        // Crea la solicitud de login
        String loginUrl = createURL("/api/usuarios/login");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        
        // Usa un objeto JSON para la solicitud de login
        String loginRequestJson = "{ \"email\": \"usuario@correo.com\", \"password\": \"password123\" }";
        HttpEntity<String> request = new HttpEntity<>(loginRequestJson, headers);

        // Realiza la solicitud POST para login
        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);

        // Verifica que la respuesta de login sea exitosa y contiene el token
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String jwtToken = response.getBody();
        assertThat(jwtToken).contains("eyJhbGciOiJIUzI1NiJ9"); // Verifica si contiene un token JWT válido
    }


    private String createURL(String uri) {
        return "http://localhost:" + port + uri;
    }
}
