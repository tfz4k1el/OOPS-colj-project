@echo off
echo ========================================================
echo  Launching Parking Lot Management System (PLMS)
echo ========================================================

set "JAVA_BIN=C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot\bin"

if exist "%JAVA_BIN%\java.exe" (
    set "JAVA=%JAVA_BIN%\java.exe"
) else (
    set "JAVA=java"
)

if not exist bin (
    echo [INFO] Binaries not found. Running build first...
    call build.bat --no-pause
)

start "" "%JAVA%" -cp bin com.plms.Main
