@echo off
echo Building Billing Service...
call mvnw.cmd clean install
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Build successful!
    echo.
    echo To run the service:
    echo   mvnw.cmd spring-boot:run
    echo.
    echo Or run the JAR:
    echo   java -jar target\service-facturation-0.0.1-SNAPSHOT.jar
) else (
    echo.
    echo Build failed! Check errors above.
    exit /b %ERRORLEVEL%
)

