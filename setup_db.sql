- prepares a MySQL server for the project

CREATE DATABASE IF NOT EXISTS defal_dev_db;
CREATE USER IF NOT EXISTS 'defal_dev'@'localhost' IDENTIFIED BY 'defal_dev_pwd';
GRANT ALL PRIVILEGES ON `defal_dev_db`.* TO 'defal_dev'@'localhost';
GRANT SELECT ON `performance_schema`.* TO 'defal_dev'@'localhost';
FLUSH PRIVILEGES;
