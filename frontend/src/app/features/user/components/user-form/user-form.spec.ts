import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';

import { UserFormComponent } from './user-form';
import { UserService } from '@features/user/services/user.service';
import { NotificationService } from '@shared/services/notification';
import { UserResponse } from '@features/user/models/response/user-response';
import { Response } from '@core/models/response';

describe('UserFormComponent', () => {
    let component: UserFormComponent;
    let fixture: ComponentFixture<UserFormComponent>;

    let userService: jasmine.SpyObj<UserService>;
    let dialogRef: jasmine.SpyObj<MatDialogRef<UserFormComponent>>;
    let notificationService: jasmine.SpyObj<NotificationService>;

    const dialogData = {
        mode: 'create' as const
    };

    const createdUser: UserResponse = {
        id: 'user-1',
        username: 'test-user',
        email: 'test@origen.cl',
        firstName: 'Test',
        lastName: 'User',
        enabled: true,
        createdAt: '2026-09-16T10:00:00Z',
        updatedAt: '2026-09-16T10:00:00Z'
    };

    beforeEach(async () => {
        userService = jasmine.createSpyObj('UserService', ['create']);

        dialogRef = jasmine.createSpyObj('MatDialogRef', ['close']);

        notificationService = jasmine.createSpyObj('NotificationService', [
            'success',
            'error'
        ]);

        await TestBed.configureTestingModule({
            imports: [UserFormComponent],
            providers: [
                {
                    provide: UserService,
                    useValue: userService
                },
                {
                    provide: MatDialogRef,
                    useValue: dialogRef
                },
                {
                    provide: MAT_DIALOG_DATA,
                    useValue: dialogData
                },
                {
                    provide: NotificationService,
                    useValue: notificationService
                }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(UserFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should require password in create mode', () => {
        const passwordControl = component.userForm.controls.password;

        passwordControl.setValue('');

        expect(passwordControl.hasError('required')).toBeTrue();
    });

    it('should not submit when the form is invalid', () => {
        component.userForm.setValue({
            username: '',
            email: 'invalid-email',
            password: '',
            firstName: '',
            lastName: '',
            enabled: true
        });

        component.submit();

        expect(userService.create).not.toHaveBeenCalled();
    });

    it('should create a user successfully', () => {
        const response: Response<UserResponse> = {
            status: 'OK',
            message: 'User created successfully.',
            data: createdUser,
            timestamp: new Date().toISOString()
        };

        userService.create.and.returnValue(of(response));

        component.userForm.setValue({
            username: 'test-user',
            email: 'test@origen.cl',
            password: 'password123',
            firstName: 'Test',
            lastName: 'User',
            enabled: true
        });

        component.submit();

        expect(userService.create).toHaveBeenCalledWith({
            username: 'test-user',
            email: 'test@origen.cl',
            password: 'password123',
            firstName: 'Test',
            lastName: 'User'
        });

        expect(dialogRef.close).toHaveBeenCalledWith({
            user: createdUser,
            message: 'User created successfully.'
        });

        expect(component.saving).toBeFalse();
    });
});
