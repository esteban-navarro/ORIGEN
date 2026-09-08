import { Component, inject } from '@angular/core';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';

import { Router } from '@angular/router';

import { TokenService } from '@core/services/token.service';
import { CurrentUserService } from '@core/services/current-user.service';

@Component({
    selector: 'app-toolbar',
    standalone: true,
    imports: [
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatMenuModule
    ],
    templateUrl: './toolbar.html',
    styleUrl: './toolbar.scss'
})
export class ToolbarComponent {

    private readonly tokenService = inject(TokenService);

    private readonly currentUserService = inject(CurrentUserService);

    private readonly router = inject(Router);

    readonly currentUser = this.getCurrentUserName();

    private getCurrentUserName(): string {

        const user = this.currentUserService.get();

        if (!user) {
            return '';
        }

        return `${user.firstName} ${user.lastName}`;
    }

    logout(): void {
        this.tokenService.clear();
        this.currentUserService.clear();
        this.router.navigate(['/login']);
    }

}
