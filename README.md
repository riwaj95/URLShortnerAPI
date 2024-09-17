# URL Shortener API

This project is a URL Shortener API built using Spring Boot. It allows users to shorten long URLs into compact, easy-to-share links and retrieve the original URL using the shortened version. The application is designed to be scalable, stateless, and easily deployable using Docker and Docker Compose.

Features

1. Shorten URLs: Converts long URLs into short, unique identifiers.
2. Retrieve Original URLs: Resolves shortened URLs back to their original form.
3. Stateless Architecture: Enables horizontal scalability by adding more instances behind a load balancer.
4. Caching: Utilizes Redis or other cache systems for performance improvements.
5. Environment Configurations: Supports multiple environments (development, staging, production) using Spring Profiles.
6. Dockerized: Can be containerized using Docker for easy deployment.
   
## Architecture Overview

The URL Shortener API follows a monolithic architecture, built using Spring Boot. It is fully containerized using Docker and designed for horizontal scalability. Key architectural components include:

Stateless Design: Ensures scalability by handling requests independently.
Relational Database: Stores URL mappings, optimized with caching for performance.
Docker: Used to package the application, ensuring consistent deployment across environments.

## Design Patterns

The API leverages several design patterns:

### Singleton Pattern: 

For managing shared resources like database connections and cache.

### Repository Pattern: 

Abstracts database interactions using Spring Data JPA.

### Service Layer Pattern: 

Separates business logic from data access and HTTP request handling.

### Builder Pattern: 

Can be applied when constructing complex entities in future enhancements.

## SOLID Principles
The application adheres to SOLID principles:

### SRP (Single Responsibility principle): 

Each class has a single responsibility, ensuring modularity.

### OCP (Open-closed principle): 

The system can be extended without modifying existing code.

### LSP (Liskov Substitution Principle): 

The repository interface allows substituting data storage mechanism.

### ISP (Interface Segregation Principle): 

The service only depends on the repository interface with no unnecessary methods.

### DIP (Dependency Inversion Principle): 

The service layer depends on abstractions (interfaces) rather than concrete implementations, ensuring flexibility.

## Deployment

The application can be easily deployed using Docker. Follow the steps below to deploy:

Build the Docker Image:

docker-compose build

Run the Application:

docker-compose up

Access the Application: Once the container is running, the API will be available at http://localhost:8080.

Environment-Specific Deployment
You can configure environment variables such as SPRING_PROFILES_ACTIVE in docker-compose.yml to switch between environments (e.g., dev, staging, prod).

### Thank you DKB factory for this exercise. There are still improvements to be made in the project. Since I am taking german classes from 10am-4pm Mon-Fri and going to job fairs, I could only do it in 2 days.Whenever I get free time, I will try to improve the code and add more features and push it in Github. Nevertheless I enjoyed the task and would like DKB for this oppurtunity.
