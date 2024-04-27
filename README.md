# Spring Boot Library Management Application

![Java](https://img.shields.io/badge/Java-11-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.5-green)
![JWT](https://img.shields.io/badge/JWT-Authentication-yellow)
![AOP](https://img.shields.io/badge/AOP-Aspect--Oriented%20Programming-red)

This is a Spring Boot-based library management application with JWT authentication and Aspect-Oriented Programming (AOP) for logging. It provides RESTful APIs for managing books, patrons, and borrowing transactions.

## Features

- **Book Management**: CRUD operations for managing books.
- **Patron Management**: CRUD operations for managing library patrons.
- **Borrowing Transactions**: APIs for borrowing and returning books by patrons.
- **JWT Authentication**: Secures APIs with JSON Web Tokens for authentication.
- **AOP Logging**: Logs method calls in service layer classes using AOP.

## Technologies Used

- **Java 21**: Programming language.
- **Spring Boot**: Framework for building Java-based web applications.
- **Spring Security**: Provides security features, including JWT authentication.
- **JWT**: JSON Web Tokens for authentication.
- **Aspect-Oriented Programming (AOP)**: Used for logging method calls.
- **Postgres Database**: In-memory database for storing library data.
- **Maven**: Dependency management and build tool.

## Getting Started

### Prerequisites

- Java 17 or higher installed on your machine.
- Maven installed on your machine.

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/noureldeen304/library-management.git
    ```

2. Navigate to the project directory:

    ```bash
    cd library-management
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    mvn spring-boot:run
    ```

5. The application will start at `http://localhost:8080`.

### Usage

- Access the API documentation and test the endpoints using Swagger UI: `http://localhost:8080/swagger-ui.html`.
- Use tools like Postman or curl to interact with the APIs.

### Configuration

- Customize application properties in `src/main/resources/application.properties` as needed.

## Contributing

Contributions are welcome! Here are some ways you can contribute:

- Report bugs or suggest features by opening an issue.
- Implement new features or fix bugs by opening a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
