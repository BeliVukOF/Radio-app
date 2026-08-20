@echo off
TITLE Git Repository Updater - Radio App
COLOR 0B

:: Podesavanje parametara
SET "PROJECT_DIR=D:\Android Studio APPS\Radio APP"
SET "GIT_PATH=C:\Program Files\Git\cmd"
SET "REPO_URL=https://github.com/BeliVukOF/Radio-app.git"
SET "GIT_EMAIL=belivuk23@gmail.com"
SET "GIT_USER=BeliVukOF"

:: Dodavanje Git-a u PATH
SET "PATH=%GIT_PATH%;%PATH%"

echo ===================================================
echo   Git Update Script (Exclusively Target Files)
echo   Target: %REPO_URL%
echo ===================================================
echo.

:: Ulazak u direktorijum projekta
cd /d "%PROJECT_DIR%"

:: Konfiguracija Git korisnika
git config user.email "%GIT_EMAIL%"
git config user.name "%GIT_USER%"

:: Inicijalizacija i osvezavanje konekcije
git init
git remote add origin %REPO_URL% 2>nul
git remote set-url origin %REPO_URL%
git branch -M main

:: VAZNO: Ponistavanje prethodno spremljenih fajlova (osigurava da se salje samo definisano)
git reset >nul 2>&1

:: Dodavanje ISKLJUCIVO 10 navedenih stavki
echo [*] Dodavanje iskljucivo odabranih fajlova...
git add -- .github app gradle .gitignore build.gradle.kts gradle.properties gradlew gradlew.bat LICENSE README.md settings.gradle.kts 2>nul

:: Upisivanje commit-a
echo [*] Pravljenje commit-a...
SET "TIMESTAMP=%DATE% %TIME%"
git commit -m "Update Radio App core files - %TIMESTAMP%"

:: Sinhronizacija sa GitHub-om pre slanja
echo [*] Preuzimanje najnovijih izmena sa GitHub-a...
git pull origin main --rebase --autostash

:: Slanje izmena na GitHub
echo [*] Slanje fajlova na GitHub (Push)...
git push -u origin main

echo.
echo ===================================================
echo   Proces je zavrsen!
echo ===================================================
echo.

:PAUSE_SCREEN
echo Prozor ostaje otvoren radi provere. Pritisni ENTER za izlaz...
set /p dummy=