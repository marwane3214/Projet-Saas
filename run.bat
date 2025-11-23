@echo off
echo Starting Billing Service...
echo.
echo Make sure PostgreSQL is running on localhost:5432
echo Database 'billingdb' must exist
echo.
pause
call mvnw.cmd spring-boot:run

