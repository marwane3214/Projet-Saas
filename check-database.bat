@echo off
echo Checking database and tables...
echo.

C:\xampp\mysql\bin\mysql.exe -u root -e "USE billingdb; SHOW TABLES;" 2>nul

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Database connection successful!
    echo.
    echo Checking table structure...
    C:\xampp\mysql\bin\mysql.exe -u root -e "USE billingdb; DESCRIBE invoices;" 2>nul
) else (
    echo.
    echo ERROR: Could not connect to database or tables don't exist!
    echo.
    echo Make sure:
    echo   1. MySQL is running in XAMPP
    echo   2. Database 'billingdb' exists
    echo   3. Tables were created by Flyway migrations
    echo.
)

pause

