import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Producto } from '../../../models/producto.model';
import { ProductoService } from '../../../services/producto.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css'],
  imports: [CommonModule]
})
export class ProductosComponent implements OnInit {
  productos: Producto[] = []; // Lista de productos a mostrar

  constructor(private productoService: ProductoService) {}

  // Inicializa el componente y carga los productos
  ngOnInit(): void {
    this.cargarProductos();
  }

  // Método para cargar la lista de productos desde el servicio
  cargarProductos() {
    this.productoService.listarProductos().subscribe({
      next: (productos: Producto[]) => {
        this.productos = productos;
      },
      error: (error: any) => {
        console.error('Error cargando productos', error);
      }
    });
  }

  // Método para eliminar un producto por su ID
  eliminarProducto(id: number) {
    this.productoService.eliminarProducto(id).subscribe({
      next: () => {
        this.cargarProductos(); // Refresca la lista de productos después de eliminar
      },
      error: (error: any) => {
        console.error('Error eliminando producto', error);
      }
    });
  }
}
