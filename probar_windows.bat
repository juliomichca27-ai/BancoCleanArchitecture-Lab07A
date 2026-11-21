@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist out mkdir out
echo Compilando programa y pruebas...
javac -encoding UTF-8 -sourcepath src\main\java -d out src\main\java\pe\edu\unsch\banco\presentacion\Main.java
if errorlevel 1 (
    echo ERROR al compilar el programa.
    pause
    exit /b 1
)
javac -encoding UTF-8 -cp out -sourcepath src\test\java -d out src\test\java\pe\edu\unsch\banco\PruebasBanco.java
if errorlevel 1 (
    echo ERROR al compilar las pruebas.
    pause
    exit /b 1
)
java -cp out pe.edu.unsch.banco.PruebasBanco
pause
