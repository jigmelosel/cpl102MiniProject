@echo off
setlocal

:: --- Step 1: Prompt for MySQL credentials ---
set /p USER=Enter MySQL username (default: root): 
if "%USER%"=="" set USER=root

set /p PASSWORD=Enter MySQL password: 

:: --- Step 2: Try to locate mysql.exe ---
for %%p in (
    "C:\xampp\mysql\bin\mysql.exe"
    "D:\xampp\mysql\bin\mysql.exe"
    "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
    "C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin\mysql.exe"
) do (
    if exist %%p (
        set MYSQL_PATH=%%p
        goto :mysql_found
    )
)

echo mysql.exe not found in common locations.
pause
exit /b

:mysql_found
echo Using MySQL at %MYSQL_PATH%
echo.
echo Creating MySQL user, database, and table...

:: --- Step 3: Execute SQL to create user, DB, and table ---
%MYSQL_PATH% -u %USER% -p%PASSWORD% -e "CREATE USER IF NOT EXISTS 'bmi_user'@'localhost' IDENTIFIED BY 'bmi123'; GRANT ALL PRIVILEGES ON bmi_calculator.* TO 'bmi_user'@'localhost'; FLUSH PRIVILEGES; CREATE DATABASE IF NOT EXISTS bmi_calculator; USE bmi_calculator; CREATE TABLE IF NOT EXISTS user_records (id INT AUTO_INCREMENT PRIMARY KEY, timestamp DATETIME NOT NULL, name VARCHAR(100) NOT NULL, height DECIMAL(5,2) NOT NULL, weight DECIMAL(5,2) NOT NULL, unit VARCHAR(10) NOT NULL, bmi DECIMAL(5,2) NOT NULL, category VARCHAR(20) NOT NULL);"

IF ERRORLEVEL 1 (
    echo MySQL user/database/table creation failed!
    pause
    exit /b 1
)

:: --- Step 4: Compile Java files ---
echo.
echo Compiling Java files...
javac -cp ".;BMI_Calculator\lib\mysql-connector-j-9.3.0.jar" BMI_Calculator\src\bmicalculator\*.java -d BMI_Calculator\bin
IF ERRORLEVEL 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

:: --- Step 5: Run the application ---
echo.
echo Running Java program...
java -cp "BMI_Calculator\bin;BMI_Calculator\lib\mysql-connector-j-9.3.0.jar" BMI_Calculator.src.bmicalculator.Main

pause
