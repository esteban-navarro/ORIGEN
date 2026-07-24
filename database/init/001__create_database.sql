/*
------------------------------------------------------------------------------
 ORIGEN Platform
 Initial Database Creation
------------------------------------------------------------------------------
 Description:
    Creates the ORIGEN database if it does not already exist.

 Notes:
    - Safe to execute multiple times.
    - Intended for initial environment setup only.
------------------------------------------------------------------------------
*/

IF DB_ID(N'ORIGEN') IS NULL
BEGIN
    PRINT 'Creating database ORIGEN...';

    CREATE DATABASE [ORIGEN];

    PRINT 'Database ORIGEN created successfully.';
END
ELSE
BEGIN
    PRINT 'Database ORIGEN already exists.';
END;
GO
