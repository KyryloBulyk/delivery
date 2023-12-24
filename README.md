# Delivery

## Overview
This project was created to implement a web page for a fast food restaurant. On this web page, you will be able to view the entire menu, as well as add dishes to the order, which can be placed for delivery and paid for immediately. The User functionality is also implemented, in which each user will need to register and log in to use the full functionality of the website.

## Features
- Authentication and authorization of users via JWT Token
- Secure API endpoints
- CRUD operations for managing User, Product, Order, Cart and Categories
- Integration with PostgreSQL for data persistence
- Real-time data updates (if applicable)
- Unit tests for all major Services
- Integration Tests for all Controllers
- Using H2 Database for Integration Tests

## Prerequisites
- Java JDK 17
- Maven
- PostgreSQL

## Setting Up the Development Environment
Provide step-by-step instructions on setting up the development environment. For instance:
1. Clone the repository:
```
git clone https://github.com/KyryloBulyk/delivery.git
```

2. Navigate to the project directory:
```
cd delivery
```

3. Install dependencies:
```
mvn install
```


## Configuring the Database
Explain how to set up the PostgreSQL database:
1. Install PostgreSQL
2. Create a new database
3. Configure database properties in `application.properties`

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
   - You can now run queries and interact with your database as required for the project.

### Troubleshooting

- If you encounter any issues, refer to PostgreSQL documentation or the error messages provided by the command line for guidance.



## Running the Application
```
mvn spring-boot:run
```


## Testing

```
mvn test
```

## Contributing
If you are open to contributions, explain how others can contribute to your project. Include guidelines for submitting pull requests, coding standards, and other requirements.


