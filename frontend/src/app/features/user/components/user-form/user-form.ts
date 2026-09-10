import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';

import { CreateUserRequest } from '@features/user/models/request/create-user-request';
import { UpdateUserRequest } from '@features/user/models/request/update-user-request';
import { UserResponse } from '@features/user/models/response/user-response';
import { UserService } from '@features/user/services/user.service';

import { NotificationService } from '@shared/services/notification';

export interface UserFormDialogData {
    mode: 'create' | 'edit';
    user?: UserResponse;
}

export interface UserFormDialogResult {
    user: UserResponse;
    message: string;
}

@Component({
    selector: 'app-user-form',
    imports: [
        ReactiveFormsModule,
        MatButtonModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule
    ],
    templateUrl: './user-form.html',
    styleUrl: './user-form.scss'
})
export class UserFormComponent {

    private readonly formBuilder = inject(FormBuilder);
    private readonly userService = inject(UserService);
    private readonly notificationService = inject(NotificationService);
    private readonly dialogRef = inject(MatDialogRef<UserFormComponent, UserFormDialogResult | undefined>);
    private readonly dialogData = inject<UserFormDialogData>(MAT_DIALOG_DATA);

    saving = false;

    readonly isEditMode = this.dialogData.mode === 'edit';

    readonly userForm = this.formBuilder.nonNullable.group({
        username: ['', [Validators.required, Validators.maxLength(50)]],
        email: ['', [Validators.required, Validators.email, Validators.maxLength(255)]],
        password: [''],
        firstName: ['', [Validators.required, Validators.maxLength(100)]],
        lastName: ['', [Validators.required, Validators.maxLength(100)]],
        enabled: [true]
    });

    constructor() {
        if (this.isEditMode && this.dialogData.user) {
            this.loadUser(this.dialogData.user);
        } else {
            this.configureCreateMode();
        }
    }

    submit(): void {
        if (this.userForm.invalid) {
            this.userForm.markAllAsTouched();
            return;
        }

        this.saving = true;

        if (this.isEditMode) {
            this.updateUser();
        } else {
            this.createUser();
        }
    }

    cancel(): void {
        if (!this.saving) {
            this.dialogRef.close();
        }
    }

    private configureCreateMode(): void {
        this.userForm.controls.password.setValidators([Validators.required, Validators.minLength(8), Validators.maxLength(255)]);
        this.userForm.controls.password.updateValueAndValidity();
    }

    private loadUser(user: UserResponse): void {
        this.userForm.patchValue({
            username: user.username,
            email: user.email,
            firstName: user.firstName,
            lastName: user.lastName,
            enabled: user.enabled
        });
    }

    private createUser(): void {
        const request: CreateUserRequest = {
            username: this.userForm.controls.username.value,
            email: this.userForm.controls.email.value,
            password: this.userForm.controls.password.value,
            firstName: this.userForm.controls.firstName.value,
            lastName: this.userForm.controls.lastName.value
        };

        this.userService.create(request).subscribe({
            next: response => {
                this.saving = false;
                this.dialogRef.close({
                    user: response.data,
                    message: response.message
                });
            },

            error: (error: HttpErrorResponse) => {
                this.saving = false;
                this.notificationService.error(
                    error.error?.message ?? 'No se pudo crear el usuario.'
                );
            }
        });
    }

    private updateUser(): void {
        const userId = this.dialogData.user?.id;

        if (!userId) {
            this.saving = false;
            this.notificationService.error('No se pudo identificar el usuario.');
            return;
        }

        const request: UpdateUserRequest = {
            username: this.userForm.controls.username.value,
            email: this.userForm.controls.email.value,
            firstName: this.userForm.controls.firstName.value,
            lastName: this.userForm.controls.lastName.value,
            enabled: this.userForm.controls.enabled.value
        };

        this.userService.update(userId, request).subscribe({
            next: response => {
                this.saving = false;
                this.dialogRef.close({
                    user: response.data,
                    message: response.message
                });
            },

            error: (error: HttpErrorResponse) => {
                this.saving = false;
                 this.notificationService.error(
                    error.error?.message ?? 'No se pudo actualizar el usuario.'
                );
            }
        });
    }
}
