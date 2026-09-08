import { Injectable } from '@angular/core';

import { AuthenticatedUser } from '@features/auth/models/response/authenticated-user';

@Injectable({
    providedIn: 'root'
})
export class CurrentUserService {

    private static readonly USER_KEY = 'origen-user';

    save(user: AuthenticatedUser): void {

        localStorage.setItem(
            CurrentUserService.USER_KEY,
            JSON.stringify(user)
        );

    }

    get(): AuthenticatedUser | null {

        const user = localStorage.getItem(
            CurrentUserService.USER_KEY
        );

        return user
            ? JSON.parse(user) as AuthenticatedUser
            : null;

    }

    clear(): void {

        localStorage.removeItem(
            CurrentUserService.USER_KEY
        );

    }

}
