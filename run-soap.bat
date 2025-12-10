@echo off
echo Starting Billing Service with SOAP API...
echo.
echo Make sure MySQL is running in XAMPP!
echo.
pause

set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

call mvnw.cmd spring-boot:run

