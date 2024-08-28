import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { LoginComponent } from './login.component';
import { UsuarioService } from '../../../services/usuario.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let mockUsuarioService: jasmine.SpyObj<UsuarioService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    // Crea un mock para el UsuarioService
    mockUsuarioService = jasmine.createSpyObj('UsuarioService', ['loginUsuario']);
    // Crea un mock para el Router
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [LoginComponent],
      providers: [
        { provide: UsuarioService, useValue: mockUsuarioService },
        { provide: Router, useValue: mockRouter },
        FormBuilder
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to /productos on successful login', () => {
    // Arrange
    const token = 'fake-token';
    mockUsuarioService.loginUsuario.and.returnValue(of(token));
    
    // Act
    component.loginForm.setValue({ email: 'test@example.com', password: 'password' });
    component.onSubmit();

    // Assert
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/productos']);
    expect(localStorage.getItem('token')).toBe(token);
  });

  it('should not navigate on failed login', () => {
    // Arrange
    mockUsuarioService.loginUsuario.and.returnValue(throwError(() => new Error('Error de autenticación')));
    
    // Act
    component.loginForm.setValue({ email: 'test@example.com', password: 'password' });
    component.onSubmit();

    // Assert
    expect(mockRouter.navigate).not.toHaveBeenCalled();
    expect(localStorage.getItem('token')).toBeNull();
  });
});
