import { Component, OnInit, inject } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

import { UserFormComponent, UserFormDialogData } from '@features/user/components/user-form/user-form';
import { UserResponse } from '@features/user/models/response/user-response';
import { UserService } from '@features/user/services/user.service';

@Component({
    selector: 'app-users',
    imports: [
        MatButtonModule,
        MatDialogModule,
        MatPaginatorModule
    ],
    templateUrl: './users.html',
    styleUrl: './users.scss'
})
export class UsersComponent implements OnInit {

    private readonly userService = inject(UserService);
    private readonly dialog = inject(MatDialog);

    users: UserResponse[] = [];

    loading = false;
    errorMessage = '';

    pageIndex = 0;
    pageSize = 10;

    ngOnInit(): void {
        this.loadUsers();
    }

    get paginatedUsers(): UserResponse[] {
        const startIndex = this.pageIndex * this.pageSize;
        const endIndex = startIndex + this.pageSize;

        return this.users.slice(startIndex, endIndex);
    }

    onPageChange(event: PageEvent): void {
        this.pageIndex = event.pageIndex;
        this.pageSize = event.pageSize;
    }

    openCreateDialog(): void {
        const dialogRef = this.dialog.open(UserFormComponent, {
            width: '700px',
            maxWidth: '95vw',
            data: {
                mode: 'create'
            } satisfies UserFormDialogData
        });

        dialogRef.afterClosed().subscribe(user => {
            if (user) {
                this.users = [...this.users, user];
            }
        });
    }

    openEditDialog(user: UserResponse): void {
        const dialogRef = this.dialog.open(UserFormComponent, {
            width: '700px',
            maxWidth: '95vw',
            data: {
                mode: 'edit',
                user
            } satisfies UserFormDialogData
        });

        dialogRef.afterClosed().subscribe(updatedUser => {
            if (updatedUser) {
                this.users = this.users.map(currentUser =>
                    currentUser.id === updatedUser.id
                        ? updatedUser
                        : currentUser
                );
            }
        });
    }

    deleteUser(user: UserResponse): void {
        const confirmed = window.confirm(
            `¿Estás seguro de que deseas eliminar al usuario "${user.username}"?`
        );

        if (!confirmed) {
            return;
        }

        this.errorMessage = '';

        this.userService.delete(user.id).subscribe({
            next: () => {
                this.users = this.users.filter(
                    currentUser => currentUser.id !== user.id
                );

                this.adjustPageAfterDelete();
            },

            error: error => {
                console.error('Error deleting user:', error);
                this.errorMessage = 'No se pudo eliminar el usuario.';
            }
        });
    }

    private loadUsers(): void {
        this.loading = true;
        this.errorMessage = '';

        this.userService.findAll().subscribe({
            next: response => {
                this.users = response.data;
                this.loading = false;
            },

            error: error => {
                console.error('Error loading users:', error);
                this.errorMessage = 'No se pudieron cargar los usuarios.';
                this.loading = false;
            }
        });
    }

    private adjustPageAfterDelete(): void {
        const totalPages = Math.ceil(this.users.length / this.pageSize);

        if (totalPages === 0) {
            this.pageIndex = 0;
            return;
        }

        const lastPageIndex = totalPages - 1;

        if (this.pageIndex > lastPageIndex) {
            this.pageIndex = lastPageIndex;
        }
    }
}
