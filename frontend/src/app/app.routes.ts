import { Routes } from '@angular/router';

import { ApplicationLayoutComponent } from './layout/application-layout/application-layout';

import { authGuard } from '@core/guards/auth.guard';

export const routes: Routes = [

    {
      path: 'login',
      loadComponent: () =>
        import('@features/auth/pages/login/login')
          .then(m => m.LoginComponent)
    },

    {
    path: '',
    component: ApplicationLayoutComponent,

    canActivate: [
      authGuard
    ],

    children: [
      {
        path: '',
        loadComponent: () =>
          import('@features/dashboard/pages/dashboard/dashboard')
            .then(m => m.DashboardComponent)
      }
    ]
  },

  {
    path: '**',
    redirectTo: ''
  }

];
