@echo off
echo ========================================================
echo  Compiling Parking Lot Management System (PLMS)
echo ========================================================

set "JAVA_BIN=C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot\bin"

if exist "%JAVA_BIN%\javac.exe" (
    set "JAVAC=%JAVA_BIN%\javac.exe"
) else (
    set "JAVAC=javac"
)

if not exist bin mkdir bin

echo Compiling Java source files...
"%JAVAC%" -encoding UTF-8 -d bin src\com\plms\*.java src\com\plms\model\*.java src\com\plms\service\*.java src\com\plms\db\*.java src\com\plms\ui\*.java

if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] Build succeeded! Classes compiled into /bin
) else (
    echo [ERROR] Build failed!
)
if "%1"=="" pause
