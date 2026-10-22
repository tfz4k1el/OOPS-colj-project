@echo off
echo ========================================================
echo  Running Automated Tests for PLMS
echo ========================================================

set "JAVA_BIN=C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot\bin"

if exist "%JAVA_BIN%\java.exe" (
    set "JAVA=%JAVA_BIN%\java.exe"
    set "JAVAC=%JAVA_BIN%\javac.exe"
) else (
    set "JAVA=java"
    set "JAVAC=javac"
)

if not exist bin mkdir bin

echo Compiling sources and tests...
"%JAVAC%" -encoding UTF-8 -d bin src\com\plms\*.java src\com\plms\model\*.java src\com\plms\service\*.java src\com\plms\db\*.java src\com\plms\ui\*.java test\com\plms\test\*.java

echo Executing tests...
"%JAVA%" -cp bin com.plms.test.PLMSTest
if "%1"=="" pause
