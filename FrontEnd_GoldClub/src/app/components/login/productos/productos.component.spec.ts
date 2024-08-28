import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { ProductosComponent } from './productos.component';
import { ProductoService } from '../../../services/producto.service';
import { Producto } from '../../../models/producto.model';

describe('ProductosComponent', () => {
  let component: ProductosComponent;
  let fixture: ComponentFixture<ProductosComponent>;
  let mockProductoService: jasmine.SpyObj<ProductoService>;

  beforeEach(async () => {
    // Crea un mock para el ProductoService
    mockProductoService = jasmine.createSpyObj('ProductoService', ['listarProductos', 'eliminarProducto']);

    await TestBed.configureTestingModule({
      imports: [ProductosComponent],
      providers: [
        { provide: ProductoService, useValue: mockProductoService }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load products on init', () => {
    // Arrange
    const productos: Producto[] = [
      { id: 1, nombre: 'Producto 1', precio: 100, descripcion: 'Descripción 1', cantidad: 10 },
      { id: 2, nombre: 'Producto 2', precio: 200, descripcion: 'Descripción 2', cantidad: 20 }
    ];
    mockProductoService.listarProductos.and.returnValue(of(productos));

    // Act
    component.ngOnInit();

    // Assert
    expect(component.productos).toEqual(productos);
  });

  it('should handle error when loading products', () => {
    // Arrange
    mockProductoService.listarProductos.and.returnValue(throwError(() => new Error('Error cargando productos')));

    // Act
    component.ngOnInit();

    // Assert
    expect(component.productos).toEqual([]);
  });

  it('should delete a product and refresh the list', () => {
    // Arrange
    const productos: Producto[] = [
      { id: 1, nombre: 'Producto 1', precio: 100, descripcion: 'Descripción 1', cantidad: 10 }
    ];
    mockProductoService.listarProductos.and.returnValue(of(productos));
    //REVISARR mockProductoService.eliminarProducto.and.returnValue(of(null));

    // Act
    component.eliminarProducto(1);

    // Assert
    expect(mockProductoService.eliminarProducto).toHaveBeenCalledWith(1);
    expect(mockProductoService.listarProductos).toHaveBeenCalled();
  });

  it('should handle error when deleting a product', () => {
    // Arrange
    mockProductoService.eliminarProducto.and.returnValue(throwError(() => new Error('Error eliminando producto')));

    // Act
    component.eliminarProducto(1);

    // Assert
    expect(mockProductoService.eliminarProducto).toHaveBeenCalledWith(1);
  });
});
