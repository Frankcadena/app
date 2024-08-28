import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductoService } from './producto.service';
import { Producto } from '../models/producto.model';

describe('ProductoService', () => {
  let service: ProductoService;
  let httpMock: HttpTestingController;
  const apiUrl = `${environment.apiUrl}/api/productos`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProductoService]
    });
    service = TestBed.inject(ProductoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Verifica que no haya solicitudes pendientes
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should list products', () => {
    const mockProducts: Producto[] = [
      { id: 1, nombre: 'Producto 1', precio: 100, descripcion: 'Desc 1', cantidad: 10 },
      { id: 2, nombre: 'Producto 2', precio: 200, descripcion: 'Desc 2', cantidad: 20 }
    ];

    service.listarProductos().subscribe(products => {
      expect(products.length).toBe(2);
      expect(products).toEqual(mockProducts);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockProducts); // Devuelve los productos simulados
  });

  it('should delete a product', () => {
    const productId = 1;

    service.eliminarProducto(productId).subscribe(response => {
      expect(response).toBe('Product deleted successfully');
    });

    const req = httpMock.expectOne(`${apiUrl}/${productId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush('Product deleted successfully', { status: 200, statusText: 'OK' }); // Simula la respuesta de eliminación
  });
});
