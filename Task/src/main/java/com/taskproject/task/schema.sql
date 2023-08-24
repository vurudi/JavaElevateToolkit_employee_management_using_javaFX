CREATE DATABASE data_source1;
USE data_source1;

-- Create COUNTRIES tableCountry_IDcountries
CREATE TABLE COUNTRIES (
  Country_ID INT PRIMARY KEY,
  Country VARCHAR(50),
  Create_Date DATETIME,
  Created_By VARCHAR(50),
  Last_Update TIMESTAMP,
  Last_Updated_By VARCHAR(50)
);

-- Create FIRST-LEVEL DIVISIONS table
CREATE TABLE FIRST_LEVEL_DIVISIONS (
  Division_ID INT PRIMARY KEY,
  Division VARCHAR(50),
  Create_Date DATETIME,
  Created_By VARCHAR(50),
  Last_Update TIMESTAMP,
  Last_Updated_By VARCHAR(50),
  Country_ID INT,
  FOREIGN KEY (Country_ID) REFERENCES COUNTRIES(Country_ID)
);

-- Create CUSTOMERS table
CREATE TABLE CUSTOMERS (
  Customer_ID INT AUTO_INCREMENT PRIMARY KEY,
  Customer_Name VARCHAR(50),
  Address VARCHAR(100),
  Postal_Code VARCHAR(50),
  Phone VARCHAR(50),
  Create_Date DATETIME,
  Created_By VARCHAR(50),
  Last_Update TIMESTAMP,
  Last_Updated_By VARCHAR(50),
  Division_ID INT,
  FOREIGN KEY (Division_ID) REFERENCES FIRST_LEVEL_DIVISIONS(Division_ID)
);


-- Create USERS table
CREATE TABLE USERS (
  User_ID INT PRIMARY KEY,
  User_Name VARCHAR(50) UNIQUE,
  Password TEXT,
  Create_Date DATETIME,
  Created_By VARCHAR(50),
  Last_Update TIMESTAMP,
  Last_Updated_By VARCHAR(50)
);

-- Create CONTACTS table
CREATE TABLE CONTACTS (
  Contact_ID INT PRIMARY KEY,
  Contact_Name VARCHAR(50),
  Email VARCHAR(50)
);

-- Create APPOINTMENTS table
CREATE TABLE APPOINTMENTS (
  Appointment_ID INT PRIMARY KEY,
  Title VARCHAR(50),
  Description VARCHAR(50),
  Location VARCHAR(50),
  Type VARCHAR(50),
  Start DATETIME,
  End DATETIME,
  Create_Date DATETIME,
  Created_By VARCHAR(50),
  Last_Update TIMESTAMP,
  Last_Updated_By VARCHAR(50),
  Customer_ID INT,
  User_ID INT,
  Contact_ID INT,
  FOREIGN KEY (Customer_ID) REFERENCES CUSTOMERS(Customer_ID),
  FOREIGN KEY (User_ID) REFERENCES USERS(User_ID),
  FOREIGN KEY (Contact_ID) REFERENCES CONTACTS(Contact_ID)
);


-- ==================SAMPLE DATA POPULATING =============

-- Add sample records to the COUNTRIES table
INSERT INTO COUNTRIES (Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By)
VALUES (1, 'United States', NOW(), 'Admin', NOW(), 'Admin');

-- Add sample records to the FIRST_LEVEL_DIVISIONS table
INSERT INTO FIRST_LEVEL_DIVISIONS (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID)
VALUES (1, 'New York', NOW(), 'Admin', NOW(), 'Admin', 1),
       (2, 'California', NOW(), 'Admin', NOW(), 'Admin', 1);

-- Add sample records to the CUSTOMERS table
INSERT INTO CUSTOMERS (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)
VALUES (1, 'ABC Company', '123 Main St', '12345', '123-456-7890', NOW(), 'Admin', NOW(), 'Admin', 1),
       (2, 'XYZ Corporation', '456 Elm St', '67890', '987-654-3210', NOW(), 'Admin', NOW(), 'Admin', 2);

-- Add sample records to the USERS table
INSERT INTO USERS (User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By)
VALUES (1, 'JohnDoe', 'password123', NOW(), 'Admin', NOW(), 'Admin'),
       (2, 'JaneSmith', 'password456', NOW(), 'Admin', NOW(), 'Admin');

-- Add sample records to the CONTACTS table
INSERT INTO CONTACTS (Contact_ID, Contact_Name, Email)
VALUES (1, 'John', 'john@example.com'),
       (2, 'Jane', 'jane@example.com');

-- Add sample records to the APPOINTMENTS table
INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)
VALUES (3, 'Meeting', 'Team meeting', 'Conference room', 'General', '2023-07-06 10:00:00', '2023-07-06 11:00:00', NOW(), 'Admin', NOW(), 'Admin', 1, 1, 1),
       (4, 'Presentation', 'Product demo', 'Boardroom', 'Sales', '2023-07-07 14:00:00', '2023-07-07 16:00:00', NOW(), 'Admin', NOW(), 'Admin', 2, 2, 2);




