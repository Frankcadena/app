package com.example.goldClub.service;

import com.example.goldClub.models.Producto;
import com.example.goldClub.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//Clase de prueba para el servicio de productos.
public class ProductoServiceTest {

 @Mock
 private ProductoRepository productoRepository; // Simula el repositorio de productos.

 @InjectMocks
 private ProductoService productoService; // Inyecta el servicio de productos en el contexto de prueba.

 public ProductoServiceTest() {
     MockitoAnnotations.openMocks(this); // Inicializa los mocks.
 }

 // Prueba para el método que lista todos los productos.
 @Test
 public void testListarProductos() {
     Producto producto = new Producto(); // Crea un producto de prueba.
     List<Producto> productos = Collections.singletonList(producto);

     // Simula el comportamiento del repositorio al listar productos.
     when(productoRepository.findAll()).thenReturn(productos);

     // Llama al método del servicio.
     List<Producto> result = productoService.listarProductos();

     // Verifica que el resultado coincida con la lista de productos esperada.
     assertEquals(productos, result);
 }

 // Prueba para el método que guarda un producto.
 @Test
 public void testGuardarProducto() {
     Producto producto = new Producto(); // Crea un producto de prueba.

     // Simula el comportamiento del repositorio al guardar un producto.
     when(productoRepository.save(producto)).thenReturn(producto);

     // Llama al método del servicio.
     Producto result = productoService.guardarProducto(producto);

     // Verifica que el producto guardado coincida con el esperado.
     assertEquals(producto, result);
 }

 // Prueba para el método que elimina un producto.
 @Test
 public void testEliminarProducto() {
     Long id = 1L; // ID del producto a eliminar.

     // Llama al método del servicio.
     productoService.eliminarProducto(id);

     // Verifica que se haya llamado al método deleteById del repositorio.
     verify(productoRepository, times(1)).deleteById(id);
 }
}
