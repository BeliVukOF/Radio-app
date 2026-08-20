@echo off
echo ========================================
echo Balkan Radio - Slanje na GitHub...
echo ========================================

:: Dodajemo sve promene
git add .

:: Kreiramo poruku sa trenutnim datumom
set current_date=%date% %time%
git commit -m "Finalized Update - %current_date%"

:: Saljemo na GitHub
echo.
echo Saljem na server...
git push origin master

:: Ako master ne postoji, probaj main
if %errorlevel% neq 0 (
    echo.
    echo Grana 'master' nije uspela, probam 'main'...
    git push origin main
)

echo.
echo ========================================
echo GOTOVO! Tvoj update je na GitHub-u.
echo ========================================
pause
