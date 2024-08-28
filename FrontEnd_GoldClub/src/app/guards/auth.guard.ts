import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) { }

  // Método que se ejecuta para determinar si se puede activar la ruta
  canActivate(): boolean {
    // Verifica si existe un token en el almacenamiento local
    const token = localStorage.getItem('token');
    if (token) {
      return true; // Permite la navegación si el token está presente
    } else {
      this.router.navigate(['/login']); // Redirige al login si el token no está presente
      return false;
    }
  }
}
