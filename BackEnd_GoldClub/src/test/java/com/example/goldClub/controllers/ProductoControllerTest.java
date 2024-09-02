package com.example.goldClub.controllers;

import com.example.goldClub.models.Producto;
import com.example.goldClub.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//Clase de prueba para el controlador de productos.
public class ProductoControllerTest {

	// Simula el servicio de productos.
	@Mock
	private ProductoService productoService;

	// Inyecta el controlador de productos en el contexto de prueba.
	@InjectMocks
	private ProductoController productoController;

	// Constructor que inicializa los mocks.
	public ProductoControllerTest() {
		MockitoAnnotations.openMocks(this);
	}

	// Prueba para el método que lista todos los productos.
	@Test
	public void testListarProductos() {
		// Crea un producto de prueba y una lista que lo contiene.
		Producto producto = new Producto();
		List<Producto> productos = Collections.singletonList(producto);

		// Simula el comportamiento del servicio al listar productos.
		when(productoService.listarProductos()).thenReturn(productos);

		// Llama al método del controlador.
		ResponseEntity<List<Producto>> response = productoController.listarProductos();

		// Verifica que la respuesta tenga un código de estado 200 (OK) y que el cuerpo
		// contenga la lista de productos.
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(productos, response.getBody());
	}

	// Prueba para el método que guarda un producto.
	@Test
	public void testGuardarProducto() {
		Producto producto = new Producto(); // Crea un producto de prueba.

		// Simula el comportamiento del servicio al guardar un producto.
		when(productoService.guardarProducto(producto)).thenReturn(producto);

		// Llama al método del controlador.
		ResponseEntity<Producto> response = productoController.guardarProducto(producto);

		// Verifica que la respuesta tenga un código de estado 200 (OK) y que el cuerpo
		// contenga el producto guardado.
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(producto, response.getBody());
	}

	// Prueba para el método que elimina un producto.
	@Test
	public void testEliminarProducto() {
		Long id = 1L; // ID del producto a eliminar.

		// Simula que no se hace nada cuando se llama al método eliminar en el servicio.
		doNothing().when(productoService).eliminarProducto(id);

		// Llama al método del controlador.
		ResponseEntity<String> response = productoController.eliminarProducto(id);

		// Verifica que la respuesta tenga un código de estado 200 (OK) y un mensaje de
		// éxito.
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Producto eliminado exitosamente", response.getBody());
	}
}
