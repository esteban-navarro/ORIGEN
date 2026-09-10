import { Component, inject, signal } from '@angular/core';

import {
    FormBuilder,
    ReactiveFormsModule,
    Validators
} from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

import { AuthService } from '@features/auth/services/auth.service';
import { LoginRequest } from '@features/auth/models/request/login-request';

import { Router } from '@angular/router';

import { TokenService } from '@core/services/token.service';
import { CurrentUserService } from '@core/services/current-user.service';

import { NotificationService } from '@shared/services/notification';

@Component({
    selector: 'app-login',
    standalone: true,

    imports: [
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatCheckboxModule,
        MatButtonModule
    ],

    templateUrl: './login.html',
    styleUrl: './login.scss'
})
export class LoginComponent {

    private readonly fb = inject(FormBuilder);

    private readonly authService = inject(AuthService);
    private readonly tokenService = inject(TokenService);
    private readonly router = inject(Router);
    private readonly notificationService = inject(NotificationService);

    readonly hidePassword = signal(true);

    readonly loginForm = this.fb.nonNullable.group({
        username: ['', [Validators.required]],
        password: ['', [Validators.required]],
        remember: [false]
    });

    private readonly currentUserService = inject(CurrentUserService);

    login(): void {
        if (this.loginForm.invalid) {
            this.loginForm.markAllAsTouched();
            return;
        }

        const request: LoginRequest = {
            username: this.loginForm.controls.username.value,
            password: this.loginForm.controls.password.value
        };

        this.authService
            .login(request)
            .subscribe({
                next: response => {
                    this.tokenService.save(response.data.accessToken);
                    this.currentUserService.save(response.data.user);
                    this.router.navigate(['/']);
                },

                error: error => {
                    console.error('Error en login:', error);
                    this.notificationService.error(
                        error.error?.message ?? 'Error al iniciar sesión.'
                    );
                }
            });

    }

}
