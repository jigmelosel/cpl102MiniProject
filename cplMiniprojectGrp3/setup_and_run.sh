#!/bin/bash

# --- Step 1: Prompt for MySQL credentials ---
read -p "Enter MySQL username (default: root): " USER
if [ -z "$USER" ]; then
  USER="root"
fi

read -s -p "Enter MySQL password: " PASSWORD
echo

# --- Step 2: Try to locate mysql ---
MYSQL_PATHS=(
  "/Applications/XAMPP/xamppfiles/bin/mysql"
  "/usr/local/mysql/bin/mysql"
  "/opt/homebrew/bin/mysql"
  "/usr/bin/mysql"
)

for path in "${MYSQL_PATHS[@]}"; do
  if [ -x "$path" ]; then
    MYSQL_PATH="$path"
    break
  fi
done

if [ -z "$MYSQL_PATH" ]; then
  echo "mysql not found in common locations."
  exit 1
fi

echo "Using MySQL at $MYSQL_PATH"
echo "Creating MySQL user, database, and table..."

# --- Step 3: Execute SQL to create user, DB, and table ---
"$MYSQL_PATH" -u "$USER" -p"$PASSWORD" -e "
CREATE USER IF NOT EXISTS 'bmi_user'@'localhost' IDENTIFIED BY 'bmi123';
GRANT ALL PRIVILEGES ON bmi_calculator.* TO 'bmi_user'@'localhost';
FLUSH PRIVILEGES;
CREATE DATABASE IF NOT EXISTS bmi_calculator;
USE bmi_calculator;
CREATE TABLE IF NOT EXISTS user_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp DATETIME NOT NULL,
    name VARCHAR(100) NOT NULL,
    height DECIMAL(5,2) NOT NULL,
    weight DECIMAL(5,2) NOT NULL,
    unit VARCHAR(10) NOT NULL,
    bmi DECIMAL(5,2) NOT NULL,
    category VARCHAR(20) NOT NULL
);"

if [ $? -ne 0 ]; then
  echo "MySQL user/database/table creation failed!"
  exit 1
fi

# --- Step 4: Compile Java files ---
echo
echo "Compiling Java files..."
javac -cp ".:BMI_Calculator/lib/mysql-connector-j-9.3.0.jar" BMI_Calculator/src/bmicalculator/*.java -d BMI_Calculator/bin

if [ $? -ne 0 ]; then
  echo "Compilation failed!"
  exit 1
fi

# --- Step 5: Run the Java application ---
echo
echo "Running Java program..."
java -cp "BMI_Calculator/bin:BMI_Calculator/lib/mysql-connector-j-9.3.0.jar" BMI_Calculator.src.bmicalculator.Main
