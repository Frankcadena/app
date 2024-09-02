package com.example.goldClub.service;

import com.example.goldClub.models.Usuario;
import com.example.goldClub.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//Clase de prueba para el servicio de usuarios.
//Utiliza JUnit para pruebas unitarias y Mockito para simular dependencias.
public class UsuarioServiceTest {

 @Mock
 private UsuarioRepository usuarioRepository; // Simula el repositorio de usuarios.

 @Mock
 private PasswordEncoder passwordEncoder; // Simula el codificador de contraseñas.

 @InjectMocks
 private UsuarioService usuarioService; // Inyecta el servicio de usuarios con las dependencias simuladas.

 // Constructor que inicializa los mocks.
 public UsuarioServiceTest() {
     MockitoAnnotations.openMocks(this);
 }

 // Prueba para el método 'registrarUsuario' del servicio de usuarios.
 @Test
 public void testRegistrarUsuario() {
     // Crea un usuario de prueba con una contraseña sin codificar.
     Usuario usuario = new Usuario();
     usuario.setPassword("password");

     // Simula el comportamiento del codificador de contraseñas.
     when(passwordEncoder.encode(usuario.getPassword())).thenReturn("encodedPassword");
     
     // Simula el comportamiento del repositorio al guardar el usuario.
     when(usuarioRepository.save(usuario)).thenReturn(usuario);

     // Llama al método de servicio que se está probando.
     Usuario result = usuarioService.registrarUsuario(usuario);

     // Verifica que la contraseña del usuario guardado esté codificada.
     assertEquals("encodedPassword", result.getPassword());
     
     // Verifica que el método 'save' del repositorio se haya llamado con el usuario.
     verify(usuarioRepository).save(usuario);
 }

 // Prueba para el método 'buscarPorEmail' del servicio de usuarios.
 @Test
 public void testBuscarPorEmail() {
     // Email de prueba para buscar.
     String email = "test@example.com";
     
     // Crea un usuario de prueba.
     Usuario usuario = new Usuario();
     
     // Simula el comportamiento del repositorio al buscar un usuario por email.
     when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

     // Llama al método de servicio que se está probando.
     Optional<Usuario> result = usuarioService.buscarPorEmail(email);

     // Verifica que el resultado sea el usuario esperado.
     assertEquals(Optional.of(usuario), result);
     
     // Verifica que el método 'findByEmail' del repositorio se haya llamado con el email.
     verify(usuarioRepository).findByEmail(email);
 }

 // Prueba para el caso en que se intenta cargar un usuario por nombre de usuario (email) que no existe.
 @Test
 public void testLoadUserByUsername_UserNotFound() {
     // Email de prueba que no existe en el repositorio.
     String email = "test@example.com";
     
     // Simula que el repositorio no encuentra un usuario con el email proporcionado.
     when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

     // Verifica que el método de servicio lanza una excepción cuando no se encuentra el usuario.
     assertThrows(UsernameNotFoundException.class, () -> usuarioService.loadUserByUsername(email));
 }
}
