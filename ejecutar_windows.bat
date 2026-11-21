@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist out mkdir out
echo Compilando sistema bancario...
javac -encoding UTF-8 -sourcepath src\main\java -d out src\main\java\pe\edu\unsch\banco\presentacion\Main.java
if errorlevel 1 (
    echo ERROR: Revisa que JDK 17 o superior este instalado y en el PATH.
    pause
    exit /b 1
)
echo Ejecutando sistema bancario...
java -cp out pe.edu.unsch.banco.presentacion.Main
pause
