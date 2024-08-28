export interface Usuario {
  id?: number; // El identificador es opcional, ya que puede no estar presente en la creación
  email: string; // El correo electrónico del usuario, obligatorio
  password: string; // La contraseña del usuario, obligatoria
  codigoEmpleado?: string; // Código de empleado opcional, utilizado solo en el registro
}
