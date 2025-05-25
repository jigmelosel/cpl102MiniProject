# BMI Calculator with MySQL Database

A Java Swing application that calculates Body Mass Index (BMI) and stores results in a MySQL database, with diet recommendations based on BMI categories.

![BMI Calculator Screenshot](screenshot.png) *(Add screenshot file later)*

## Prerequisites

### 1. Software Requirements
- **Java Development Kit (JDK) 11+** [Download](https://adoptium.net/)
- **XAMPP with MySQL** [Download](https://www.apachefriends.org/)
- **MySQL Connector/J** (included in `lib/` folder)

### 2. Database Setup (Must Do Before First Run)
1. Start XAMPP and launch MySQL
2. Execute these commands in MySQL:

### If you want to change the username or password in the sql code given bellow, change must be maded in the program code.
### To test the application, just stik with the codes and i am giving.


### for windows directly double click the run_bmi.bat file
### for macOS navigate to where the setup_and_run.sh file is in terminal and give permision to setup_and_run.sh by chmod +x setup_and_run.sh
### and to execute it ./setup_and_run.sh

```sql
CREATE DATABASE bmi_calculator;
USE bmi_calculator;

CREATE TABLE user_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp DATETIME NOT NULL,
    name VARCHAR(100) NOT NULL,
    height DECIMAL(5,2) NOT NULL,
    weight DECIMAL(5,2) NOT NULL,
    unit VARCHAR(10) NOT NULL,
    bmi DECIMAL(5,2) NOT NULL,
    category VARCHAR(20) NOT NULL
);

CREATE USER 'bmi_user'@'localhost' IDENTIFIED BY 'bmi123';
GRANT ALL PRIVILEGES ON bmi_calculator.* TO 'bmi_user'@'localhost';
FLUSH PRIVILEGES;

