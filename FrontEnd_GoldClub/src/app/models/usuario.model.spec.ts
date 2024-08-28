import { Usuario } from './usuario.model';

describe('Usuario', () => {
  it('should create an instance', () => {
    // Crea una instancia de Usuario para verificar que se pueda crear correctamente
    const usuario: Usuario = { email: 'test@example.com', password: 'password' };
    expect(usuario).toBeTruthy();
  });
});
