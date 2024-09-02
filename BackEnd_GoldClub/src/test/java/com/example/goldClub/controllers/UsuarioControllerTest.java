package com.example.goldClub.controllers;

import com.example.goldClub.models.Usuario;
import com.example.goldClub.models.Empleado;
import com.example.goldClub.security.JwtService;
import com.example.goldClub.service.UsuarioService;
import com.example.goldClub.service.EmpleadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//Clase de prueba para el controlador de usuarios.
public class UsuarioControllerTest {

 @Mock
 private UsuarioService usuarioService; // Simula el servicio de usuarios.

 @Mock
 private EmpleadoService empleadoService; // Simula el servicio de empleados.

 @Mock
 private JwtService jwtService; // Simula el servicio de JWT.

 @Mock
 private PasswordEncoder passwordEncoder; // Simula el codificador de contraseñas.

 @InjectMocks
 private UsuarioController usuarioController; // Inyecta el controlador de usuarios en el contexto de prueba.

 @BeforeEach
 public void setUp() {
     MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada prueba.
 }

 // Prueba para registrar un usuario exitosamente.
 @Test
 public void testRegistrarUsuario_Exitoso() {
     Usuario usuario = new Usuario(); // Crea un usuario de prueba.
     usuario.setCodigoEmpleado(123); // Asigna un código de empleado válido.
     Empleado empleado = new Empleado(); // Crea un empleado de prueba.

     // Simula el comportamiento de los servicios.
     when(empleadoService.buscarPorCodigoEmpleado(usuario.getCodigoEmpleado())).thenReturn(Optional.of(empleado));
     when(usuarioService.registrarUsuario(usuario)).thenReturn(usuario);

     // Llama al método del controlador.
     ResponseEntity<Usuario> response = usuarioController.registrarUsuario(usuario);

     // Verifica que la respuesta tenga un código de estado 200 (OK) y contenga el usuario registrado.
     assertEquals(200, response.getStatusCodeValue());
     assertEquals(usuario, response.getBody());
 }

 // Prueba para el inicio de sesión exitoso de un usuario.
 @Test
 public void testLoginUsuario_Exitoso() {
     Usuario usuario = new Usuario(); // Crea un usuario de prueba.
     usuario.setEmail("julian@example.com");
     usuario.setPassword("123456789");
     String token = "testToken"; // Token de prueba.

     // Simula el comportamiento de los servicios.
     when(usuarioService.buscarPorEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
     when(passwordEncoder.matches(usuario.getPassword(), usuario.getPassword())).thenReturn(true);
     when(jwtService.generateToken(usuario)).thenReturn(token);

     // Llama al método del controlador.
     ResponseEntity<String> response = usuarioController.loginUsuario(usuario);

     // Verifica que la respuesta tenga un código de estado 200 (OK) y contenga el token generado.
     assertEquals(200, response.getStatusCodeValue());
     assertEquals(token, response.getBody());
 }

 // Prueba para el inicio de sesión con credenciales incorrectas.
 @Test
 public void testLoginUsuario_InvalidCredentials() {
     Usuario usuario = new Usuario(); // Crea un usuario de prueba.
     usuario.setEmail("julian@example.com");

     // Simula el comportamiento del servicio cuando no se encuentra el usuario.
     when(usuarioService.buscarPorEmail(usuario.getEmail())).thenReturn(Optional.empty());

     // Llama al método del controlador.
     ResponseEntity<String> response = usuarioController.loginUsuario(usuario);

     // Verifica que la respuesta tenga un código de estado 401 (No autorizado) y un mensaje de error.
     assertEquals(401, response.getStatusCodeValue());
     assertEquals("Credenciales incorrectas", response.getBody());
 }
}
