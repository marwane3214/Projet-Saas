@echo off
echo Creating billingdb database...
echo.
echo Make sure MySQL is running in XAMPP!
echo.

REM Try to connect and create database
REM First try XAMPP default location
if exist "C:\xampp\mysql\bin\mysql.exe" (
    "C:\xampp\mysql\bin\mysql.exe" -u root -e "CREATE DATABASE IF NOT EXISTS billingdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" 2>nul
) else (
    REM Try system PATH
    mysql -u root -e "CREATE DATABASE IF NOT EXISTS billingdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" 2>nul
)

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Database 'billingdb' created successfully!
    echo.
) else (
    echo.
    echo Could not create database automatically.
    echo Please create it manually using one of these methods:
    echo.
    echo Method 1: Using phpMyAdmin
    echo   1. Open http://localhost/phpmyadmin
    echo   2. Click "New" in the left sidebar
    echo   3. Enter database name: billingdb
    echo   4. Click "Create"
    echo.
    echo Method 2: Using MySQL command line
    echo   mysql -u root
    echo   CREATE DATABASE billingdb;
    echo.
    pause
)

