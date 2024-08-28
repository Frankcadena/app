import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { importProvidersFrom } from '@angular/core';
import { routes } from './app/app.routes';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app/app.component';

// Arranca la aplicación Angular
bootstrapApplication(AppComponent, {
  providers: [
    // Provee la configuración de rutas para la aplicación
    provideRouter(routes),
    // Importa el módulo HttpClientModule para las solicitudes HTTP
    importProvidersFrom(HttpClientModule)
  ]
}).catch(err => console.error(err)); // Captura y muestra errores en la consola
