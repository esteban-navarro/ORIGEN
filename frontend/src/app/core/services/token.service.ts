import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class TokenService {

    private static readonly TOKEN_KEY = 'origen-token';

    save(token: string): void {

        localStorage.setItem(
            TokenService.TOKEN_KEY,
            token
        );

    }

    get(): string | null {

        return localStorage.getItem(
            TokenService.TOKEN_KEY
        );

    }

    clear(): void {

        localStorage.removeItem(
            TokenService.TOKEN_KEY
        );

    }

    hasToken(): boolean {

        return this.get() !== null;

    }

}
