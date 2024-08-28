import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Producto } from '../models/producto.model';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = `${environment.apiUrl}/api/productos`; // URL base para la API de productos

  constructor(private http: HttpClient) {}

  /**
   * Obtiene los headers necesarios para las solicitudes HTTP, incluyendo el token de autorización.
   * @returns {HttpHeaders} Headers con el token de autorización.
   */
  private obtenerHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Obtiene el token del almacenamiento local
    return new HttpHeaders({
      'Authorization': `Bearer ${token}` // Configura el header Authorization con el token
    });
  }

  /**
   * Lista todos los productos desde la API.
   * @returns {Observable<Producto[]>} Un observable que emite una lista de productos.
   */
  listarProductos(): Observable<Producto[]> {
    const headers = this.obtenerHeaders();
    return this.http.get<Producto[]>(this.apiUrl, { headers });
  }

  /**
   * Elimina un producto por su ID.
   * Dado que la API puede devolver texto plano, manejamos la respuesta como texto.
   * @param {number} id - El ID del producto a eliminar.
   * @returns {Observable<string>} Un observable que emite una cadena de texto con la respuesta de la eliminación.
   */
  eliminarProducto(id: number): Observable<string> {
    const headers = this.obtenerHeaders();
    return this.http.delete(`${this.apiUrl}/${id}`, { headers, responseType: 'text' }) as Observable<string>;
  }
}
