@echo off
if not exist out mkdir out
javac -encoding UTF-8 -cp "lib\*" -d out src\*.java
if errorlevel 1 pause & exit /b 1
java -cp "out;lib\*" Main
pause
