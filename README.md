# Getting Started with the Application

This README provides instructions on how to set up and test the application.

## Prerequisites:

*   Java Development Kit (JDK) installed and configured.
*   Maven installed and configured.

## Steps:

1.  **Start the Application:**
    
    Run the application using Maven:
    
    ```bash
    mvn spring-boot:run
    ```
    
    The application will start on port 8080.
    
2.  **Access the H2 Console:**
    
    Open your web browser and navigate to `http://localhost:8080/h2-console`. This will open the H2 console, which allows you to interact with the database.
    
3.  **Find the JDBC URL:**
    
    In the H2 console, locate the "JDBC URL" field. Copy the JDBC URL from this field.
    
4.  **Execute the SQL Script:**
    
    Navigate to the `src/main/resources/data.sql` file. This file contains SQL commands to insert initial data into the database. Execute the insert commands in the `data.sql` file using the JDBC URL obtained from the H2 console.
    
5.  **Test the Application:**
    
    Open your web browser and navigate to `http://localhost:8080/owners/1`. This will display information about the owner with ID 1.

## Note:

*   The `data.sql` file contains sample data for testing purposes. You can modify or add your own data as needed.
*   The application uses an in-memory H2 database, which means the data will be lost when the application is stopped. For persistent data storage, you can configure the application to use a different database like MySQL or PostgreSQL.

Enjoy using the application!

mvn clean install
java -jar utapCattle-0.0.1-SNAPSHOT.jar 
