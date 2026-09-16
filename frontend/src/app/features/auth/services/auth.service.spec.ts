import { TestBed } from '@angular/core/testing';
import {
    HttpTestingController,
    provideHttpClientTesting
} from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { AuthService } from './auth.service';
import { LoginRequest } from '@features/auth/models/request/login-request';
import { Response } from '@core/models/response';
import { LoginResponse } from '@features/auth/models/response/login-response';
import { API_CONFIG } from '@core/configuration/api.config';

describe('AuthService', () => {
    let service: AuthService;
    let httpTesting: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                AuthService,
                provideHttpClient(),
                provideHttpClientTesting()
            ]
        });

        service = TestBed.inject(AuthService);
        httpTesting = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpTesting.verify();
    });

    it('should login successfully', () => {
        const request: LoginRequest = {
            username: 'admin',
            password: 'password123'
        };

        const response: Response<LoginResponse> = {
            status: 'OK',
            message: 'Login successful.',
            data: {
                accessToken: 'test-token',
                tokenType: 'Bearer',
                expiresIn: 3600,
                user: {
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
                }
            },
            timestamp: new Date().toISOString()
        };

        service.login(request).subscribe(result => {
            expect(result).toEqual(response);
        });

        const httpRequest = httpTesting.expectOne(
            `${API_CONFIG.baseUrl}/auth/login`
        );

        expect(httpRequest.request.method).toBe('POST');
        expect(httpRequest.request.body).toEqual(request);

        httpRequest.flush(response);
    });
});
