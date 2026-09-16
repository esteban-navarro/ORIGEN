import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

import { LoginComponent } from './login';

import { AuthService } from '@features/auth/services/auth.service';
import { TokenService } from '@core/services/token.service';
import { CurrentUserService } from '@core/services/current-user.service';
import { NotificationService } from '@shared/services/notification';

import { Response } from '@core/models/response';
import { LoginResponse } from '@features/auth/models/response/login-response';
import { AuthenticatedUser } from '@features/auth/models/response/authenticated-user';

describe('LoginComponent', () => {
    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;

    let authService: jasmine.SpyObj<AuthService>;
    let tokenService: jasmine.SpyObj<TokenService>;
    let currentUserService: jasmine.SpyObj<CurrentUserService>;
    let notificationService: jasmine.SpyObj<NotificationService>;
    let router: jasmine.SpyObj<Router>;

    beforeEach(async () => {
        authService = jasmine.createSpyObj('AuthService', ['login']);
        tokenService = jasmine.createSpyObj('TokenService', ['save']);
        currentUserService = jasmine.createSpyObj('CurrentUserService', ['save']);
        notificationService = jasmine.createSpyObj('NotificationService', ['error']);
        router = jasmine.createSpyObj('Router', ['navigate']);

        await TestBed.configureTestingModule({
            imports: [LoginComponent],
            providers: [
                { provide: AuthService, useValue: authService },
                { provide: TokenService, useValue: tokenService },
                { provide: CurrentUserService, useValue: currentUserService },
                { provide: NotificationService, useValue: notificationService },
                { provide: Router, useValue: router }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(LoginComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should not call AuthService when the form is invalid', () => {
        component.login();

        expect(authService.login).not.toHaveBeenCalled();
    });

    it('should login successfully, save token and user, and navigate to home', () => {
        const user: AuthenticatedUser = {
            id: 'user-id',
            username: 'admin',
            email: 'admin@origen.cl',
            firstName: 'Admin',
            lastName: 'Origen',
            roles: ['ADMIN'],
            permissions: [
                'USER_READ',
                'USER_CREATE',
                'USER_UPDATE',
                'USER_DELETE'
            ]
        };

        const loginResponse: Response<LoginResponse> = {
            status: 'OK',
            message: 'Login successful.',
            data: {
                accessToken: 'test-token',
                tokenType: 'Bearer',
                expiresIn: 3600,
                user
            },
            timestamp: new Date().toISOString()
        };

        component.loginForm.setValue({
            username: 'admin',
            password: '123456',
            remember: false
        });

        authService.login.and.returnValue(of(loginResponse));

        component.login();

        expect(authService.login).toHaveBeenCalledWith({
            username: 'admin',
            password: '123456'
        });

        expect(tokenService.save).toHaveBeenCalledWith('test-token');
        expect(currentUserService.save).toHaveBeenCalledWith(user);
        expect(router.navigate).toHaveBeenCalledWith(['/']);
    });

    it('should show an error notification when login fails', () => {
        component.loginForm.setValue({
            username: 'admin',
            password: 'wrong-password',
            remember: false
        });

        authService.login.and.returnValue(
            throwError(() => ({
                error: {
                    message: 'Invalid credentials.'
                }
            }))
        );

        component.login();

        expect(notificationService.error).toHaveBeenCalledWith(
            'Invalid credentials.'
        );
    });
});
