import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import {
    DeleteUserDialogComponent,
    DeleteUserDialogData
} from './delete-user-dialog';

describe('DeleteUserDialogComponent', () => {
    let component: DeleteUserDialogComponent;
    let fixture: ComponentFixture<DeleteUserDialogComponent>;

    let dialogRef: jasmine.SpyObj<MatDialogRef<DeleteUserDialogComponent, boolean>>;

    const dialogData: DeleteUserDialogData = {
        username: 'test-user'
    };

    beforeEach(async () => {
        dialogRef = jasmine.createSpyObj('MatDialogRef', ['close']);

        await TestBed.configureTestingModule({
            imports: [DeleteUserDialogComponent],
            providers: [
                {
                    provide: MatDialogRef,
                    useValue: dialogRef
                },
                {
                    provide: MAT_DIALOG_DATA,
                    useValue: dialogData
                }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(DeleteUserDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should receive the username from dialog data', () => {
        expect(component.dialogData.username).toBe('test-user');
    });

    it('should close the dialog with false when cancel is called', () => {
        component.cancel();

        expect(dialogRef.close).toHaveBeenCalledWith(false);
    });

    it('should close the dialog with true when confirm is called', () => {
        component.confirm();

        expect(dialogRef.close).toHaveBeenCalledWith(true);
    });
});
