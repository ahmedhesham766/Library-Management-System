# Library Management System API

## Table of Contents
- [Introduction](#introduction)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Authentication and Authorization](#authentication-and-authorization)
- [Postman Collection](#postman-collection)

## Introduction
The Library Management System API allows librarians to manage books, patrons, and borrowing records. The application is built using Spring Boot and provides various endpoints for handling operations related to books and patrons, including borrowing and returning books.

## Running the Application

### Prerequisites
- Java 17
- Maven
- A relational database (configured in `application.properties`)

### Steps to Run

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd <repository-directory>
   
2. app.prop

    ```properties
    spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=Library;integratedSecurity=false;trustServerCertificate=true;
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

## Creating the `Library` Database Manually in SQL Server

Before running the application, you need to create a database called `Library` in SQL Server. Follow the steps below:

1. **Open SQL Server Management Studio (SSMS):**
    - Launch SQL Server Management Studio on your computer.

2. **Connect to Your SQL Server Instance:**
    - Enter your server name, and connect using your credentials (e.g., SQL Server Authentication or Windows Authentication).

3. **Create the `Library` Database:**
    - Open a new query window by clicking on `New Query`.
    - Run the following SQL command to create the database:

   ```sql
   CREATE DATABASE Library;

## API Endpoints

### Authentication

### 1. Register a New User

- **Endpoint:** `POST /api/register`
- **Description:** Registers a new user in the system.
- **Request Body:**

  ```json
  {
      "username": "user",
      "password": "password123"
  }
### 2. Authenticate a User

- **Endpoint:** `POST /api/authenticate`
- **Description:** Authenticates a user and returns a JWT token.
- **Request Body:**

  ```json
  {
      "username": "user",
      "password": "password123"
  }


- **Response:**
   - **200 OK**: Returns a JSON object containing the JWT token. Example response:

     ```json
     {
         "token": "your-jwt-token-here"
     }
     ```

- **Usage:**
   - The JWT token returned in the response must be included in the `Authorization` header of subsequent requests to access secured API endpoints.

   - Example of how to include the JWT in the header:

     ```
     Authorization: Bearer your-jwt-token-here
     ```
## API Testing with Postman

To make it easier for you to test the API endpoints, a Postman collection has been created. This collection contains all the API endpoints for the Library Management System, along with example requests and responses.

### Postman Collection

- **Link to Postman Collection:** [Library Management System APIs](https://www.postman.com/red-shuttle-713002/workspace/librarymanagmentsysapis/collection/22530413-097743a8-9a36-4fe9-831c-0ae9d7729b0f?action=share&creator=22530413)

### How to Use the Postman Collection

1. **Open the Link:**
   - Click on the link above to open the Postman collection in your browser.

2. **Import the Collection:**
   - If you're logged in to your Postman account, you can directly import the collection into your workspace by clicking the "Fork" or "Import" button.

3. **Set Up Environment Variables:**
   - The collection might require environment variables like `base_url` or `token`. Make sure to set these up in Postman.
   - `base_url`: Set this to `http://localhost:8080` if you're running the application locally.
   - `token`: After authenticating, use the JWT token provided in the response to set the `token` variable. This will allow you to access the secured endpoints.

4. **Run the Requests:**
   - Select any request from the collection and click "Send" to execute it.
   - The collection includes sample requests for creating, updating, and retrieving books, as well as borrowing and returning books.

5. **View Responses:**
   - After sending a request, you can view the response in Postman, which will include the status code, response body, and headers.

This Postman collection simplifies the process of testing the API and allows you to quickly verify the functionality of each endpoint without needing to manually construct HTTP requests.
