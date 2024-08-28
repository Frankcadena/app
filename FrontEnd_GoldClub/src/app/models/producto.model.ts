export interface Producto {
  id: number; // Identificador único del producto, obligatorio
  nombre: string; // Nombre del producto, obligatorio
  precio: number; // Precio del producto, obligatorio
  descripcion: string; // Descripción del producto, obligatoria
  cantidad: number; // Cantidad en stock del producto, obligatoria
}
