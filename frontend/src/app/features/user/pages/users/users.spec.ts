import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { of } from 'rxjs';

import { UsersComponent } from './users';
import { UserService } from '@features/user/services/user.service';
import { UserResponse } from '@features/user/models/response/user-response';
import { Response } from '@core/models/response';
import { NotificationService } from '@shared/services/notification';
import { CurrentUserService } from '@core/services/current-user.service';

describe('UsersComponent', () => {
    let component: UsersComponent;
    let fixture: ComponentFixture<UsersComponent>;

    let userService: jasmine.SpyObj<UserService>;
    let dialog: jasmine.SpyObj<MatDialog>;
    let notificationService: jasmine.SpyObj<NotificationService>;
    let currentUserService: jasmine.SpyObj<CurrentUserService>;

    const users: UserResponse[] = [
        {
            id: 'user-1',
            username: 'admin',
            email: 'admin@origen.cl',
            firstName: 'Admin',
            lastName: 'Origen',
            enabled: true,
            createdAt: '2026-09-01T10:00:00Z',
            updatedAt: '2026-09-01T10:00:00Z'
        },
        {
            id: 'user-2',
            username: 'user',
            email: 'user@origen.cl',
            firstName: 'Test',
            lastName: 'User',
            enabled: true,
            createdAt: '2026-09-01T10:00:00Z',
            updatedAt: '2026-09-01T10:00:00Z'
        }
    ];

    beforeEach(async () => {
        userService = jasmine.createSpyObj('UserService', [
            'findAll',
            'delete'
        ]);

        dialog = jasmine.createSpyObj('MatDialog', ['open']);

        notificationService = jasmine.createSpyObj('NotificationService', [
            'success',
            'error'
        ]);

        currentUserService = jasmine.createSpyObj('CurrentUserService', [
            'hasPermission'
        ]);

        await TestBed.configureTestingModule({
            imports: [UsersComponent],
            providers: [
                {
                    provide: UserService,
                    useValue: userService
                },
                {
                    provide: NotificationService,
                    useValue: notificationService
                },
                {
                    provide: CurrentUserService,
                    useValue: currentUserService
                }
            ]
        }).overrideProvider(MatDialog, {
              useValue: dialog
          })
          .compileComponents();

        fixture = TestBed.createComponent(UsersComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should load users successfully', () => {
        const response: Response<UserResponse[]> = {
            status: 'OK',
            message: 'Users retrieved successfully.',
            data: users,
            timestamp: new Date().toISOString()
        };

        userService.findAll.and.returnValue(of(response));

        component.ngOnInit();

        expect(userService.findAll).toHaveBeenCalled();
        expect(component.dataSource.data).toEqual(users);
        expect(component.loading).toBeFalse();
    });

    it('should check user permissions', () => {
        currentUserService.hasPermission.and.callFake(permission => {
            return permission === 'USER_CREATE';
        });

        expect(component.hasCreatePermission()).toBeTrue();
        expect(component.hasUpdatePermission()).toBeFalse();
        expect(component.hasDeletePermission()).toBeFalse();

        expect(currentUserService.hasPermission).toHaveBeenCalledWith('USER_CREATE');
        expect(currentUserService.hasPermission).toHaveBeenCalledWith('USER_UPDATE');
        expect(currentUserService.hasPermission).toHaveBeenCalledWith('USER_DELETE');
    });

    it('should delete a user when deletion is confirmed', () => {
        component.dataSource.data = [...users];

        const dialogRef = jasmine.createSpyObj<
            MatDialogRef<unknown, boolean>
        >('MatDialogRef', ['afterClosed']);

        dialogRef.afterClosed.and.returnValue(of(true));

        dialog.open.and.returnValue(dialogRef);

        userService.delete.and.returnValue(
            of({
                status: 'OK',
                message: 'User deleted successfully.',
                data: undefined,
                timestamp: new Date().toISOString()
            } as Response<void>)
        );

        component.deleteUser(users[0]);

        expect(dialog.open).toHaveBeenCalled();
        expect(userService.delete).toHaveBeenCalledWith('user-1');
        expect(component.dataSource.data).toEqual([users[1]]);
        expect(notificationService.success).toHaveBeenCalledWith(
            'User deleted successfully.'
        );
    });
});
