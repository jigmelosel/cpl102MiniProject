# 🧮 BMI Indicator GUI - Java Swing + MySQL Project

This project is a **Body Mass Index (BMI) Indicator with Database Support**, built using **Java Swing** and **MySQL**. It visually displays a user's BMI on a vertical scale with categories and stores BMI calculation records into a MySQL database for tracking.

---

## 📌 Project Description

This Java Swing-based GUI application allows users to input their height and weight to calculate their BMI. It then:

* Displays the BMI on a vertical color-coded scale.
* Shows the health category (underweight, normal, overweight, obese).
* Stores each calculation in a MySQL database with timestamp.

---

## 🕈 Features

* Java Swing-based user interface
* Dynamic BMI indicator panel
* BMI pointer and category label
* Reversed scale (underweight at bottom)
* MySQL database integration
* Automatic timestamping of each BMI record
* Clean file separation and modular code

---

## 📊 WHO BMI Classification

| Category          | BMI Range | Color       |
| ----------------- | --------- | ----------- |
| Severe Thinness   | < 16      | Light Blue  |
| Moderate Thinness | 16 - 17   | Medium Blue |
| Mild Thinness     | 17 - 18.5 | Dark Blue   |
| Normal            | 18.5 - 25 | Green       |
| Overweight        | 25 - 30   | Yellow      |
| Obese Class I     | 30 - 35   | Orange      |
| Obese Class II    | 35 - 40   | Red         |
| Obese Class III   | > 40      | Dark Red    |

---

## 📂 File Structure

```
BMI-Indicator-GUI/
├── BMIIndicator.java        # Custom Swing panel for BMI scale
├── BMIGUI.java              # Main GUI interface
├── BMIDatabase.java         # Handles MySQL DB connection and queries
├── bmi_records.sql          # SQL script to create the database table
├── README.md                # Project documentation
```

---

## 🚧 Setup Instructions

### 1. Software Requirements
- **Java Development Kit (JDK) 11+** [Download](https://adoptium.net/)
- **XAMPP with MySQL** [Download](https://www.apachefriends.org/)
- **MySQL Connector/J** (included in `lib/` folder)

### 2. Database Setup (Must Do Before First Run)
1. Start XAMPP and launch MySQL
2. Execute these commands in MySQL:


### If you want to change the username or password in the sql code given bellow, change must be maded in the program code.


```sql
CREATE DATABASE bmi_db;
USE bmi_db;

CREATE TABLE bmi_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    height_cm DOUBLE NOT NULL,
    weight_kg DOUBLE NOT NULL,
    bmi DOUBLE NOT NULL,
    category VARCHAR(50),
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE USER 'bmi_user'@'localhost' IDENTIFIED BY 'bmi123';
GRANT ALL PRIVILEGES ON bmi_calculator.* TO 'bmi_user'@'localhost';
FLUSH PRIVILEGES;
```

### ✅ Configure `BMIDatabase.java`

Edit the MySQL connection variables:

```java
String url = "jdbc:mysql://localhost:3306/bmi_db";
String user = "root";            // your MySQL username
String password = "yourpass";    // your MySQL password
```

---

## 🚀 How to Run

1. Compile the project:

```bash
javac BMIIndicator.java BMIGUI.java BMIDatabase.java
```

2. Run the main class:

```bash
java BMIGUI
```

---

## 🌍 How It Works

* User enters height (cm) and weight (kg).
* BMI is calculated and shown visually.
* The result is saved in the MySQL `bmi_records` table.
* Pointer dynamically updates position on the scale.
* Categories are based on WHO standards.

---

## 🎉 Future Improvements

* BMI history graph/chart
* Export to PDF or CSV
* User login & profiles
* JavaFX UI upgrade

---

## 👤 Author

* **Dorji Yangzom**
* **Dron Chhetri** 
* **Jigme Losel** 
* **Karma Galey** 

---

## 📈 Acknowledgements

* BMI guidelines from [World Health Organization (WHO)](https://www.who.int/news-room/fact-sheets/detail/obesity-and-overweight)
* Java Swing documentation
* MySQL Connector/J


### for windows directly double click the run_bmi.bat file
### for macOS navigate to where the setup_and_run.sh file is in terminal and give permision to setup_and_run.sh by chmod +x setup_and_run.sh
### and to execute it ./setup_and_run.sh
