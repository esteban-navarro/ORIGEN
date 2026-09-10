import { Component, inject } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

export interface DeleteUserDialogData {
    username: string;
}

@Component({
    selector: 'app-delete-user-dialog',
    imports: [
        MatButtonModule,
        MatDialogModule,
        MatIconModule
    ],
    templateUrl: './delete-user-dialog.html',
    styleUrl: './delete-user-dialog.scss'
})
export class DeleteUserDialogComponent {

    private readonly dialogRef = inject(MatDialogRef<DeleteUserDialogComponent, boolean>);

    readonly dialogData = inject<DeleteUserDialogData>(MAT_DIALOG_DATA);

    cancel(): void {
        this.dialogRef.close(false);
    }

    confirm(): void {
        this.dialogRef.close(true);
    }
}
