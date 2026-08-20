@echo off
TITLE Git Repository Updater - Radio App
COLOR 0B

:: Podešavanje parametara
SET "PROJECT_DIR=D:\Android Studio APPS\Radio APP"
SET "GIT_PATH=C:\Program Files\Git\cmd"
SET "REPO_URL=https://github.com/BeliVukOF/Radio-app.git"
SET "GIT_EMAIL=belivuk23@gmail.com"
SET "GIT_USER=BeliVukOF"

:: Dodavanje Git-a u PATH za tekući prozor
SET "PATH=%GIT_PATH%;%PATH%"

echo ===================================================
echo   Git Repo Update Script: Radio App
echo   Target: %REPO_URL%
echo ===================================================
echo.

:: Ulazak u direktorijum projekta
cd /d "%PROJECT_DIR%"
if %errorlevel% neq 0 (
    echo [GRESKA] Putanja %PROJECT_DIR% ne postoji! Proveri folder.
    pause
    exit /b
)

:: Podešavanje autorstva
git config user.email "%GIT_EMAIL%"
git config user.name "%GIT_USER%"

:: Provera inicijalizacije
if not exist ".git" (
    echo [*] Inicijalizacija novog Git repo-a...
    git init
    git remote add origin %REPO_URL%
) else (
    echo [*] Osvezavam link ka repozitorijumu...
    git remote set-url origin %REPO_URL%
)

:: Preimenovanje trenutne grane (master) u main radi mečovanja sa GitHub-om
git branch -M main

:: Dodavanje svih izmena u tracking
echo [*] Dodavanje svih izmena...
git add .

:: Kreiranje commit-a sa trenutnim izmenama
echo [*] Kreiranje commit-a...
SET "TIMESTAMP=%DATE% %TIME%"
git commit -m "Update Radio App - %TIMESTAMP%"

:: Sinhronizacija sa GitHub-om (Pull pre Push-a)
echo [*] Preuzimanje najnovijeg stanja sa GitHub-a...
git pull origin main --rebase --autostash

:: Slanje izmena na GitHub
echo [*] Slanje na GitHub (Push)...
git push -u origin main

echo.
if %errorlevel% equ 0 (
    echo ===================================================
    echo   USPESNO! Tvoj repo na GitHub-u je osvezen!
    echo ===================================================
) else (
    echo ===================================================
    echo   [GRESKA] Proveri da li si prijavljen na GitHub.
    echo ===================================================
)

echo.
pause