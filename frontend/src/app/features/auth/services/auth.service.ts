import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { API_CONFIG } from '@core/configuration/api.config';

import { Response } from '@core/models/response';

import { LoginRequest } from '@features/auth/models/login-request';
import { LoginResponse } from '@features/auth/models/login-response';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private readonly http = inject(HttpClient);

    private readonly apiUrl = `${API_CONFIG.baseUrl}/auth`;

    login(request: LoginRequest): Observable<Response<LoginResponse>> {

        return this.http.post<Response<LoginResponse>>(
            `${this.apiUrl}/login`,
            request
        );

    }

}
