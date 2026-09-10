import { Component, OnInit, inject, ViewChild } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';

import { UserFormComponent, UserFormDialogData, UserFormDialogResult } from '@features/user/components/user-form/user-form';
import { UserResponse } from '@features/user/models/response/user-response';
import { UserService } from '@features/user/services/user.service';
import { DeleteUserDialogComponent, DeleteUserDialogData } from '@features/user/components/delete-user-dialog/delete-user-dialog';

import { NotificationService } from '@shared/services/notification';

import { CurrentUserService } from '@core/services/current-user.service';

@Component({
    selector: 'app-users',
    imports: [
        MatButtonModule,
        MatDialogModule,
        MatPaginatorModule,
        MatIconModule,
        MatTooltipModule,
        MatTableModule
    ],
    templateUrl: './users.html',
    styleUrl: './users.scss'
})
export class UsersComponent implements OnInit {

    private readonly userService = inject(UserService);
    private readonly dialog = inject(MatDialog);
    private readonly notificationService = inject(NotificationService);
    private readonly currentUserService = inject(CurrentUserService);

    readonly dataSource = new MatTableDataSource<UserResponse>([]);

    readonly displayedColumns = [
        'username',
        'email',
        'name',
        'status',
        'actions'
    ];

    @ViewChild(MatPaginator)
    set paginator(paginator: MatPaginator) {
        this.dataSource.paginator = paginator;
    }

    loading = false;

    ngOnInit(): void {
        this.loadUsers();
    }

    openCreateDialog(): void {
        const dialogRef = this.dialog.open(UserFormComponent, {
            width: '700px',
            maxWidth: '95vw',
            data: {
                mode: 'create'
            } satisfies UserFormDialogData
        });

        dialogRef.afterClosed().subscribe((result: UserFormDialogResult | undefined) => {
            if (result) {
                this.dataSource.data = [...this.dataSource.data, result.user];
                this.notificationService.success(result.message);
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

        dialogRef.afterClosed().subscribe((result: UserFormDialogResult | undefined) => {
            if (result) {
                this.dataSource.data = this.dataSource.data.map(currentUser =>
                    currentUser.id === result.user.id
                        ? result.user
                        : currentUser
                );
                this.notificationService.success(result.message);
            }
        });
    }

    deleteUser(user: UserResponse): void {
      const dialogRef = this.dialog.open(
          DeleteUserDialogComponent,
          {
              width: '500px',
              maxWidth: '95vw',
              data: {
                  username: user.username
              } satisfies DeleteUserDialogData
          }
      );

      dialogRef.afterClosed().subscribe(confirmed => {
          if (!confirmed) {
              return;
          }

          this.userService.delete(user.id).subscribe({
              next: response => {
                  this.dataSource.data = this.dataSource.data.filter(
                      currentUser => currentUser.id !== user.id
                  );
                  this.notificationService.success(response.message);
              },

              error: error => {
                  console.error('Error deleting user:', error);

                  this.notificationService.error(
                      error.error?.message ?? 'No se pudo eliminar el usuario.'
                  );
              }
          });
      });
    }

    private loadUsers(): void {
        this.loading = true;

        this.userService.findAll().subscribe({
            next: response => {
                this.dataSource.data = response.data;
                this.loading = false;
            },

            error: error => {
                console.error('Error loading users:', error);
                this.notificationService.error('No se pudieron cargar los usuarios.');
                this.loading = false;
            }
        });
    }

    hasCreatePermission(): boolean {
        return this.currentUserService.hasPermission('USER_CREATE');
    }

    hasUpdatePermission(): boolean {
        return this.currentUserService.hasPermission('USER_UPDATE');
    }

    hasDeletePermission(): boolean {
        return this.currentUserService.hasPermission('USER_DELETE');
    }

}
