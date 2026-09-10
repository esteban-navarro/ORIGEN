import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from '@core/interceptors/auth.interceptor';
import { routes } from './app.routes';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { SpanishPaginatorIntl } from '@core/configuration/mat-paginator-intl';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({
      eventCoalescing: true
    }),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([
          authInterceptor
      ])
    ),
    {
      provide: MatPaginatorIntl,
      useClass: SpanishPaginatorIntl
    }
  ]
};
