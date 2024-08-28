import { Producto } from './producto.model';

describe('Producto', () => {
  it('should create an instance', () => {
    // Crea una instancia de Producto para verificar que se pueda crear correctamente
    const producto: Producto = {
      id: 1,
      nombre: 'Producto Test',
      precio: 100,
      descripcion: 'Descripción del producto',
      cantidad: 10
    };
    expect(producto).toBeTruthy();
  });
});
