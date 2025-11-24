@echo off
echo ========================================
echo Push vers GitHub: Projet-Saas
echo ========================================
echo.

cd /d "%~dp0"

echo [1/4] Configuration du remote...
"C:\Program Files\Git\bin\git.exe" remote set-url origin https://github.com/marwane3214/Projet-Saas.git

echo.
echo [2/4] Verification du remote...
"C:\Program Files\Git\bin\git.exe" remote -v

echo.
echo [3/4] Verification de l'etat...
"C:\Program Files\Git\bin\git.exe" status

echo.
echo [4/4] Push vers GitHub...
echo ATTENTION: Vous devrez peut-etre vous authentifier
echo.
"C:\Program Files\Git\bin\git.exe" push -u origin main

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo SUCCES! Le code a ete pousse vers GitHub
    echo ========================================
    echo.
    echo Verifiez sur: https://github.com/marwane3214/Projet-Saas
    echo.
) else (
    echo.
    echo ========================================
    echo ERREUR lors du push
    echo ========================================
    echo.
    echo Solutions possibles:
    echo 1. Authentification requise - utilisez un Personal Access Token
    echo 2. Verifiez vos permissions sur le depot
    echo 3. Le depot existe-t-il sur GitHub?
    echo.
)

pause

