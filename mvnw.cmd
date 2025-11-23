@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@IF "%DEBUG%"=="" @ECHO OFF
@SETLOCAL

SET MAVEN_SKIP_RC=true

@REM Determine the current working directory
set "MAVEN_CMD_BASEDIR=%CD%"

@REM Resolve Java command
set JAVA_EXE=java.exe
if defined JAVA_HOME (
  if exist "%JAVA_HOME%\bin\java.exe" set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
)

@REM Find project base dir
set EXEC_DIR=%CD%
set WDIR=%EXEC_DIR%
if exist "%WDIR%\.mvn" goto gotMaven
cd ..
set WDIR=%CD%
if exist "%WDIR%\.mvn" goto gotMaven
cd ..
set WDIR=%CD%
if exist "%WDIR%\.mvn" goto gotMaven
cd ..
set WDIR=%CD%
if exist "%WDIR%\.mvn" goto gotMaven
cd %EXEC_DIR%
goto finish

:gotMaven
set MAVEN_PROJECTBASEDIR=%WDIR%
cd /d "%EXEC_DIR%"

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@REM Download maven-wrapper.jar if not present
if exist %WRAPPER_JAR% goto afterWrapper

set WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

@REM Try PowerShell with TLS 1.2
powershell -NoProfile -ExecutionPolicy Bypass -Command "try { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%WRAPPER_URL%', '%WRAPPER_JAR%') } catch { exit 1 }"
if exist %WRAPPER_JAR% goto afterWrapper

@REM Try curl if available
where curl >NUL 2>&1
if %ERRORLEVEL%==0 (
  curl -L -o %WRAPPER_JAR% %WRAPPER_URL%
)
if exist %WRAPPER_JAR% goto afterWrapper

@REM Try BITSAdmin as last resort
where bitsadmin >NUL 2>&1
if %ERRORLEVEL%==0 (
  bitsadmin /transfer "mvnwWrapper" /download /priority normal %WRAPPER_URL% %WRAPPER_JAR%
)
if exist %WRAPPER_JAR% goto afterWrapper

echo Failed to download maven-wrapper.jar from %WRAPPER_URL%
exit /b 1

:afterWrapper
%JAVA_EXE% -classpath %WRAPPER_JAR% %WRAPPER_LAUNCHER% %*
set ERROR_CODE=%ERRORLEVEL%

:finish
ENDLOCAL & set ERROR_CODE=%ERROR_CODE%
exit /b %ERROR_CODE%


