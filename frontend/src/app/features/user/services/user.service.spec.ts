import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
    HttpTestingController,
    provideHttpClientTesting
} from '@angular/common/http/testing';

import { UserService } from './user.service';
import { UserResponse } from '@features/user/models/response/user-response';
import { CreateUserRequest } from '@features/user/models/request/create-user-request';
import { Response } from '@core/models/response';
import { API_CONFIG } from '@core/configuration/api.config';

describe('UserService', () => {
    let service: UserService;
    let httpTesting: HttpTestingController;

    const user: UserResponse = {
        id: 'user-1',
        username: 'admin',
        email: 'admin@origen.cl',
        firstName: 'Admin',
        lastName: 'Origen',
        enabled: true,
        createdAt: '2026-09-16T12:00:00Z',
        updatedAt: '2026-09-16T12:00:00Z'
    };

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                UserService,
                provideHttpClient(),
                provideHttpClientTesting()
            ]
        });

        service = TestBed.inject(UserService);
        httpTesting = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpTesting.verify();
    });

    it('should find all users', () => {
        const response: Response<UserResponse[]> = {
            status: 'OK',
            message: 'Users retrieved successfully.',
            data: [user],
            timestamp: new Date().toISOString()
        };

        service.findAll().subscribe(result => {
            expect(result).toEqual(response);
        });

        const httpRequest = httpTesting.expectOne(
            `${API_CONFIG.baseUrl}/users`
        );

        expect(httpRequest.request.method).toBe('GET');

        httpRequest.flush(response);
    });

    it('should create a user', () => {
        const request: CreateUserRequest = {
            username: 'new-user',
            email: 'new-user@origen.cl',
            password: 'password123',
            firstName: 'New',
            lastName: 'User'
        };

        const response: Response<UserResponse> = {
            status: 'OK',
            message: 'User created successfully.',
            data: user,
            timestamp: new Date().toISOString()
        };

        service.create(request).subscribe(result => {
            expect(result).toEqual(response);
        });

        const httpRequest = httpTesting.expectOne(
            `${API_CONFIG.baseUrl}/users`
        );

        expect(httpRequest.request.method).toBe('POST');
        expect(httpRequest.request.body).toEqual(request);

        httpRequest.flush(response);
    });

    it('should delete a user', () => {
        const userId = 'user-1';

        const response: Response<void> = {
            status: 'OK',
            message: 'User deleted successfully.',
            data: undefined,
            timestamp: new Date().toISOString()
        };

        service.delete(userId).subscribe(result => {
            expect(result).toEqual(response);
        });

        const httpRequest = httpTesting.expectOne(
            `${API_CONFIG.baseUrl}/users/${userId}`
        );

        expect(httpRequest.request.method).toBe('DELETE');

        httpRequest.flush(response);
    });
});
