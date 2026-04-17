CREATE DATABASE IF NOT EXISTS record_management_db;
USE record_management_db;

CREATE TABLE IF NOT EXISTS records (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    department VARCHAR(100) NOT NULL,
    marks DOUBLE NOT NULL
);