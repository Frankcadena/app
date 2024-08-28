import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../services/usuario.service';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [ReactiveFormsModule, CommonModule]
})
export class RegisterComponent {
  registerForm: FormGroup; // Formulario de registro
  errorMessage: string | null = null; // Mensaje de error a mostrar

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router
  ) {
    // Inicializa el formulario con validaciones
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      codigoEmpleado: ['', [Validators.required, Validators.minLength(3)]], // Validación para código de empleado
      password: ['', [Validators.required, Validators.minLength(6)]] // Validación de longitud mínima para contraseña
    });
  }

  // Método que se ejecuta al enviar el formulario
  onSubmit() {
    if (this.registerForm.valid) {
      // Llama al servicio para registrar al usuario
      this.usuarioService.registrarUsuario(this.registerForm.value).subscribe(
        () => {
          this.router.navigate(['/login']); // Redirige al login después del registro exitoso
        },
        error => {
          console.error('Error en el registro', error);
          this.errorMessage = 'Error en el registro. Intente nuevamente.'; // Muestra un mensaje de error al usuario
        }
      );
    } else {
      this.errorMessage = 'Por favor complete el formulario correctamente.'; // Mensaje de validación
    }
  }
}
