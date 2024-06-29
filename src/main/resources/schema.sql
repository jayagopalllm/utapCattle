CREATE SCHEMA cattleco;

CREATE TABLE cattleco.OWNER (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            age INT NOT NULL);