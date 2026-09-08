import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { API_CONFIG } from '@core/configuration/api.config';
import { Response } from '@core/models/response';

import { CreateUserRequest } from '@features/user/models/request/create-user-request';
import { UpdateUserRequest } from '@features/user/models/request/update-user-request';
import { UserResponse } from '@features/user/models/response/user-response';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private readonly http = inject(HttpClient);

    private readonly apiUrl = `${API_CONFIG.baseUrl}/users`;

    findAll(): Observable<Response<UserResponse[]>> {

        return this.http.get<Response<UserResponse[]>>(
            this.apiUrl
        );

    }

    findById(id: string): Observable<Response<UserResponse>> {

        return this.http.get<Response<UserResponse>>(
            `${this.apiUrl}/${id}`
        );

    }

    create(request: CreateUserRequest): Observable<Response<UserResponse>> {

        return this.http.post<Response<UserResponse>>(
            this.apiUrl,
            request
        );

    }

    update(
        id: string,
        request: UpdateUserRequest
    ): Observable<Response<UserResponse>> {

        return this.http.put<Response<UserResponse>>(
            `${this.apiUrl}/${id}`,
            request
        );

    }

    delete(id: string): Observable<Response<void>> {

        return this.http.delete<Response<void>>(
            `${this.apiUrl}/${id}`
        );

    }

}
