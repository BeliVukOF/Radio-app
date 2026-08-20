@echo off
TITLE Git Auto Sync - Radio App
COLOR 0A

:: Podešavanje promenljivih
SET "PROJECT_DIR=D:\Android Studio APPS\Radio APP"
SET "GIT_PATH=C:\Program Files\Git\cmd"
SET "REPO_URL=https://github.com/BeliVukOF/Radio-app.git"
SET "GIT_EMAIL=belivuk23@gmail.com"
SET "GIT_USER=BeliVukOF"

:: Dodavanje Git-a u sistemski PATH za ovu sesiju
SET "PATH=%GIT_PATH%;%PATH%"

echo ===================================================
echo   Automatski Git Sync za Radio App (BeliVukOF)
echo ===================================================
echo.

:: Ulazak u direktorijum projekta
cd /d "%PROJECT_DIR%"
if %errorlevel% neq 0 (
    echo [GRESKA] Putanja %PROJECT_DIR% ne postoji!
    pause
    exit /b
)

:: Podešavanje Git korisnika za ovaj projekat
git config user.email "%GIT_EMAIL%"
git config user.name "%GIT_USER%"

:: Provera da li je repo već inicijalizovan
if not exist ".git" (
    echo [*] Inicijalizacija novog Git repozitorijuma...
    git init
    git remote add origin %REPO_URL%
    git branch -M main
) else (
    echo [*] Git je vec inicijalizovan. Osvezavam remote URL...
    git remote set-url origin %REPO_URL%
)

:: OPTIMIZACIJA: Čišćenje build i cache fajlova pre uploada
echo [*] Optimizacija projekta (ciscenje privremenih build fajlova)...
if exist "gradlew.bat" (
    call gradlew.bat clean
) else (
    if exist "build" rd /s /q "build"
    if exist "app\build" rd /s /q "app\build"
    if exist ".gradle" rd /s /q ".gradle"
)

:: Preuzimanje izmena ako postoje na GitHub-u (izbegavanje konflikata)
echo [*] Provera i preuzimanje izmena sa GitHub-a...
git pull origin main --rebase

:: Dodavanje svih fajlova, pravljenje commit-a i push
echo [*] Dodavanje fajlova u Git tracking...
git add .

echo [*] Kreiranje commit-a...
SET "TIMESTAMP=%DATE% %TIME%"
git commit -m "Auto update - Radio App (%TIMESTAMP%)"

echo [*] Slanje na GitHub (Push)...
git push -u origin main

echo.
if %errorlevel% equ 0 (
    echo ===================================================
    echo   USPESNO! Tvoj projekat je sinhronizovan na GitHub.
    echo ===================================================
) else (
    echo ===================================================
    echo   [GRESKA] Doslo je do greske prilikom push-ovanja.
    echo   Sacekaj proveru GitHub tokena ili kredencijala.
    echo ===================================================
)

echo.
pause