SET XACT_ABORT ON;

BEGIN TRANSACTION;

------------------------------------------------------------
-- USERS
------------------------------------------------------------

CREATE TABLE users
(
    id UNIQUEIDENTIFIER NOT NULL,
    username NVARCHAR(50) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    first_name NVARCHAR(100) NOT NULL,
    last_name NVARCHAR(100) NOT NULL,
    enabled BIT NOT NULL
        CONSTRAINT DF_users_enabled
        DEFAULT (1),
    created_at DATETIME2 NOT NULL
        CONSTRAINT DF_users_created_at
        DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NOT NULL
        CONSTRAINT DF_users_updated_at
        DEFAULT SYSUTCDATETIME(),

    CONSTRAINT PK_users
        PRIMARY KEY CLUSTERED (id)
);

CREATE UNIQUE INDEX UX_users_username
    ON users(username);

CREATE UNIQUE INDEX UX_users_email
    ON users(email);

------------------------------------------------------------
-- ROLES
------------------------------------------------------------

CREATE TABLE roles
(
    id UNIQUEIDENTIFIER NOT NULL,
    name NVARCHAR(50) NOT NULL,
    description NVARCHAR(255) NULL,
    created_at DATETIME2 NOT NULL
        CONSTRAINT DF_roles_created_at
        DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NOT NULL
        CONSTRAINT DF_roles_updated_at
        DEFAULT SYSUTCDATETIME(),

    CONSTRAINT PK_roles
        PRIMARY KEY CLUSTERED (id)
);

CREATE UNIQUE INDEX UX_roles_name
    ON roles(name);

------------------------------------------------------------
-- PERMISSIONS
------------------------------------------------------------

CREATE TABLE permissions
(
    id UNIQUEIDENTIFIER NOT NULL,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255) NULL,
    created_at DATETIME2 NOT NULL
        CONSTRAINT DF_permissions_created_at
        DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NOT NULL
        CONSTRAINT DF_permissions_updated_at
        DEFAULT SYSUTCDATETIME(),

    CONSTRAINT PK_permissions
        PRIMARY KEY CLUSTERED (id)
);

CREATE UNIQUE INDEX UX_permissions_name
    ON permissions(name);

------------------------------------------------------------
-- USER_ROLES
------------------------------------------------------------

CREATE TABLE user_roles
(
    user_id UNIQUEIDENTIFIER NOT NULL,
    role_id UNIQUEIDENTIFIER NOT NULL,

    CONSTRAINT PK_user_roles
        PRIMARY KEY CLUSTERED (user_id, role_id),

    CONSTRAINT FK_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT FK_user_roles_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);

CREATE INDEX IX_user_roles_role_id
    ON user_roles(role_id);

------------------------------------------------------------
-- ROLE_PERMISSIONS
------------------------------------------------------------

CREATE TABLE role_permissions
(
    role_id UNIQUEIDENTIFIER NOT NULL,
    permission_id UNIQUEIDENTIFIER NOT NULL,

    CONSTRAINT PK_role_permissions
        PRIMARY KEY CLUSTERED (role_id, permission_id),

    CONSTRAINT FK_role_permissions_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE,

    CONSTRAINT FK_role_permissions_permission
        FOREIGN KEY (permission_id)
        REFERENCES permissions(id)
        ON DELETE CASCADE
);

CREATE INDEX IX_role_permissions_permission_id
    ON role_permissions(permission_id);

------------------------------------------------------------
-- INITIAL DATA
------------------------------------------------------------

DECLARE @ROLE_ADMIN UNIQUEIDENTIFIER = NEWID();
DECLARE @ROLE_USER UNIQUEIDENTIFIER = NEWID();

DECLARE @PERMISSION_USER_READ UNIQUEIDENTIFIER = NEWID();
DECLARE @PERMISSION_USER_CREATE UNIQUEIDENTIFIER = NEWID();
DECLARE @PERMISSION_USER_UPDATE UNIQUEIDENTIFIER = NEWID();
DECLARE @PERMISSION_USER_DELETE UNIQUEIDENTIFIER = NEWID();

------------------------------------------------------------
-- ROLES
------------------------------------------------------------

INSERT INTO roles
(
    id,
    name,
    description
)
VALUES
(
    @ROLE_ADMIN,
    'ADMIN',
    'System administrator'
),
(
    @ROLE_USER,
    'USER',
    'Standard application user'
);

------------------------------------------------------------
-- PERMISSIONS
------------------------------------------------------------

INSERT INTO permissions
(
    id,
    name,
    description
)
VALUES
(
    @PERMISSION_USER_READ,
    'USER_READ',
    'Read users'
),
(
    @PERMISSION_USER_CREATE,
    'USER_CREATE',
    'Create users'
),
(
    @PERMISSION_USER_UPDATE,
    'USER_UPDATE',
    'Update users'
),
(
    @PERMISSION_USER_DELETE,
    'USER_DELETE',
    'Delete users'
);

------------------------------------------------------------
-- ROLE PERMISSIONS
------------------------------------------------------------

INSERT INTO role_permissions
(
    role_id,
    permission_id
)
VALUES
(@ROLE_ADMIN, @PERMISSION_USER_READ),
(@ROLE_ADMIN, @PERMISSION_USER_CREATE),
(@ROLE_ADMIN, @PERMISSION_USER_UPDATE),
(@ROLE_ADMIN, @PERMISSION_USER_DELETE),
(@ROLE_USER, @PERMISSION_USER_READ);

COMMIT TRANSACTION;
