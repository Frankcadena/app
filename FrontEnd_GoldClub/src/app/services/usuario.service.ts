import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = `${environment.apiUrl}/api/usuarios`; // URL base para la API de usuarios

  constructor(private http: HttpClient) {}

  /**
   * Registra un nuevo usuario.
   * @param {Usuario} usuario - El objeto Usuario con los datos del nuevo usuario.
   * @returns {Observable<Usuario>} Un observable que emite el usuario registrado.
   */
  registrarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/registro`, usuario);
  }

  /**
   * Inicia sesión de un usuario.
   * @param {Usuario} usuario - El objeto Usuario con el email y la contraseña.
   * @returns {Observable<string>} Un observable que emite el token de autenticación como una cadena de texto.
   */
  loginUsuario(usuario: Usuario): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/login`, usuario, { responseType: 'text' as 'json' });
  }
}
