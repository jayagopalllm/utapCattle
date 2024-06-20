CREATE SCHEMA cattleco;

CREATE TABLE cattleco.OWNER (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            age INT NOT NULL);

INSERT INTO cattleco.OWNER (id, name, age) VALUES (1, 'John Doe', 30);
INSERT INTO cattleco.OWNER (id, name, age) VALUES (2, 'Jane Smith', 25);
