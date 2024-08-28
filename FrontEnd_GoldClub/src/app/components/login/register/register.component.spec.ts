import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { RegisterComponent } from './register.component';
import { UsuarioService } from '../../../services/usuario.service';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let mockUsuarioService: jasmine.SpyObj<UsuarioService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    // Crea mocks para los servicios y el enrutador
    mockUsuarioService = jasmine.createSpyObj('UsuarioService', ['registrarUsuario']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [RegisterComponent, ReactiveFormsModule, CommonModule],
      providers: [
        { provide: UsuarioService, useValue: mockUsuarioService },
        { provide: Router, useValue: mockRouter }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display error message if form is invalid', () => {
    // Act
    component.onSubmit();

    // Assert
    expect(component.errorMessage).toBe('Por favor complete el formulario correctamente.');
  });

  it('should register user and navigate on successful registration', () => {
    // Arrange
    const formValue = { email: 'test@example.com', codigoEmpleado: '123', password: 'password' };
    component.registerForm.setValue(formValue);
    //REVISARR mockUsuarioService.registrarUsuario.and.returnValue(of(null));

    // Act
    component.onSubmit();

    // Assert
    expect(mockUsuarioService.registrarUsuario).toHaveBeenCalledWith(formValue);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should display error message on registration failure', () => {
    // Arrange
    const formValue = { email: 'test@example.com', codigoEmpleado: '123', password: 'password' };
    component.registerForm.setValue(formValue);
    mockUsuarioService.registrarUsuario.and.returnValue(throwError(() => new Error('Error en el registro')));

    // Act
    component.onSubmit();

    // Assert
    expect(component.errorMessage).toBe('Error en el registro. Intente nuevamente.');
  });
});
