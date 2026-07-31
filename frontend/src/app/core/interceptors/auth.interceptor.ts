import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { TokenService } from '@core/services/token.service';

const PUBLIC_ENDPOINTS = [
    '/api/v1/auth/login',
    '/api/v1/status'
];

export const authInterceptor: HttpInterceptorFn = (request, next) => {

    const isPublic = PUBLIC_ENDPOINTS.some(endpoint =>
        request.url.endsWith(endpoint)
    );

    if (isPublic) {

        return next(request);

    }

    const token = inject(TokenService).get();

    if (!token) {

        return next(request);

    }

    return next(

        request.clone({

            setHeaders: {

                Authorization: `Bearer ${token}`

            }

        })

    );

};
