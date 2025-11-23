@echo off
echo Setting up Java 17 for this project...
echo.

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo JAVA_HOME set to: %JAVA_HOME%
echo.
java -version
echo.
echo Java 17 is now configured!
echo.
echo To make this permanent, add to System Environment Variables:
echo   JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot
echo   Add %JAVA_HOME%\bin to PATH
echo.
pause


