@echo off
setlocal
where java >nul 2>nul || (
  echo Java was not found. Install a 64-bit JDK 17 and add it to PATH.
  exit /b 1
)
where gradle >nul 2>nul || (
  echo Gradle was not found. Install Gradle 8 or open the project in IntelliJ IDEA and use its Gradle integration.
  exit /b 1
)
gradle --version
gradle clean build
if errorlevel 1 exit /b 1
echo.
echo Build finished. Check build\libs\umbral_dualblades-0.1.0.jar
endlocal
