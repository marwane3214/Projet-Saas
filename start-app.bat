@echo off
echo ========================================
echo Starting Billing Service with SOAP API
echo ========================================
echo.

REM Set Java 17
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Java Version:
java -version
echo.

echo Checking MySQL...
REM Check if MySQL is accessible (optional check)
echo.

echo Starting application...
echo Please wait 20-30 seconds for application to start...
echo.

REM Set Maven options to fix wrapper issue
set MAVEN_OPTS=-Dmaven.multiModuleProjectDirectory=%CD%

REM Run the application
call mvnw.cmd spring-boot:run

pause

