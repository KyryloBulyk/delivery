# Delivery


## Overview
This project implements a web page for a fast-food restaurant. Users can view menus, add dishes to their order, place deliveries, and make payments. The system features user authentication, allowing full access only to registered and logged-in users.


## Frontend part
This repository contains the Backend part of the project. The corresponding Frontend part can be found in the following repository: [Delivery-App Frontend](https://github.com/oXide0/Delivery-App).


## Features
- User authentication and authorization via JWT Tokens
- Secure API endpoints
- CRUD operations for User, Product, Order, Cart, and Categories
- PostgreSQL integration for data persistence
- Real-time data updates (where applicable)
- Comprehensive unit tests for Services
- Integration tests for all Controllers
- H2 Database usage for Integration Tests


## Prerequisites
- Java JDK 17
- Maven
- PostgreSQL


## Setting Up the Development Environment
1. **Clone the Repository**:
    ```bash
   git clone https://github.com/KyryloBulyk/delivery.git
    ```

2. Navigate to the project directory:
    ```bash
    cd delivery
    ```

3. Install dependencies:
    ```bash
    mvn install
    ```


## Database Installation Guide

This guide walks you through setting up the `deliverydb.sql` database locally using PostgreSQL.

### Installation Steps

1. **Open the PostgreSQL Command Line Tool**:
   - On Windows, search for `psql` in the Start menu.
   - On MacOS or Linux, open a terminal and type `psql`.

2. **Log in to PostgreSQL**:
   - Use the following command, replacing `[username]` with your PostgreSQL username:
     ```bash
     psql -U [username]
     ```

3. **Create the Database**:
   - Run the following SQL command to create a new database:
     ```sql
     CREATE DATABASE deliverydb;
     ```

4. **Exit psql**:
   - Type `\q` and hit Enter to exit.

5. **Import the SQL File**:
   - Navigate to the directory containing `deliverydb.sql`.
   - Run the command below, replacing `[username]` with your PostgreSQL username:
     ```bash
     psql -U [username] -d deliverydb -f deliverydb.sql
     ```

6. **Verify the Import**:
   - Log back into PostgreSQL:
     ```bash
     psql -U [username] deliverydb
     ```
   - Check if tables and data are imported correctly:
     ```sql
     \dt
     ```

7. **Start Using the Database**:
   - Update the `spring.datasource.username` and `spring.datasource.password` fields in `application.properties` with your PostgreSQL credentials.


### Troubleshooting

- If you encounter any issues, refer to PostgreSQL documentation or the error messages provided by the command line for guidance.


## Running the Application
Execute the following command to start the application:

    ```bash
    mvn spring-boot:run
    ```

## Testing
Run the following command to execute Unit Tests and Integration Tests:

    ```bash
    mvn test
    ```


## Contributing
Contributions to the project are welcome. Please adhere to the following guidelines:
- Fork the repository and create a new branch for your feature.
- Write clean, commented, and tested code.
- Submit a pull request with a clear description of the changes.

Your feedback and contributions are highly appreciated!