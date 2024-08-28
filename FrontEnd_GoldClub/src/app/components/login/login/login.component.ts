import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../services/usuario.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup; // Formulario reactivo para el login

  constructor(
    private fb: FormBuilder, // Inyecta FormBuilder para construir el formulario
    private usuarioService: UsuarioService, // Servicio para manejar la autenticación de usuarios
    public router: Router // Inyecta Router para la navegación
  ) {
    // Inicializa el formulario de login con validaciones
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // Campo de email con validación requerida y formato de correo
      password: ['', Validators.required] // Campo de contraseña con validación requerida
    });
  }

  // Método que se ejecuta al enviar el formulario
  onSubmit() {
    if (this.loginForm.valid) {
      // Llama al servicio para autenticar al usuario
      this.usuarioService.loginUsuario(this.loginForm.value).subscribe(
        (token: string) => {
          // Guarda el token en el almacenamiento local
          localStorage.setItem('token', token);
          // Navega a la página de productos
          this.router.navigate(['/productos']);
        },
        (error: any) => {
          // Muestra un mensaje de error si la autenticación falla
          console.error('Error de autenticación', error);
        }
      );
    }
  }
}
