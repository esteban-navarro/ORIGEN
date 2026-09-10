import { Injectable, inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root'
})
export class NotificationService {

    private readonly snackBar = inject(MatSnackBar);

    success(message: string): void {
        this.snackBar.open(message, 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'right',
            verticalPosition: 'bottom',
            panelClass: ['snackbar-success']
        });
    }

    error(message: string): void {
        this.snackBar.open(message, 'Cerrar', {
            duration: 5000,
            horizontalPosition: 'right',
            verticalPosition: 'bottom',
            panelClass: ['snackbar-error']
        });
    }

    warning(message: string): void {
        this.snackBar.open(message, 'Cerrar', {
            duration: 5000,
            horizontalPosition: 'right',
            verticalPosition: 'bottom',
            panelClass: ['snackbar-warning']
        });
    }
}
