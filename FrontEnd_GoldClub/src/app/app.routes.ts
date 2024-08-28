import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { ProductosComponent } from './components/login/productos/productos.component';
import { RegisterComponent } from './components/login/register/register.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'productos', component: ProductosComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirige al login en lugar de mostrar LoginComponent
  { path: '**', redirectTo: '/login' } // Redirige cualquier ruta desconocida a login
];
