# Job Portal

A backend REST API service for the Job Portal app, built with Java. This backend manages user authentication, job listings, applications, and employer data. It connects to a MongoDB database, currently configured to run in a Docker container but can also be configured to connect to a local MongoDB instance.

> **Status:** In Progress  
> Features and design are actively evolving.

## Features

- RESTful API endpoints for jobs, users, profiles, and applications
- Session based user authentication and authorization
- MongoDB database integration for data persistence
- Dockerized MongoDB setup for easy development and testing
- Configurable connection to local or remote MongoDB instances

## Tech Stack

- **Language:** Java
- **Build Tool:** javac, Java CLI
- **Database:** MongoDB
- **Containerization:** Docker (for MongoDB)
- **Libraries:** MongoDB Java Driver, JSON parsing libraries

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Docker (for running MongoDB container)
- MongoDB (optional, if you want to run local instance)
- Internet connection for downloading dependencies

## Setup

### MongoDB via Docker (Recommended)

1. Start MongoDB container:
    ```bash
    docker run -d --name jobportal-mongo -p 27017:27017 mongo:latest
    ```
2. Ensure MongoDB is accessible on mongodb://localhost:27017.

### Local MongoDB Instance (Optional)

1. Install MongoDB on your machine: [MongoDB Installation Guide](https://www.mongodb.com/docs/manual/installation/)
2. Start MongoDB service locally.
3. Update the connection string in your configuration file to:
```bash
mongodb://localhost:27017
```

## Compile

Compile the Java source code with dependencies:
```bash
javac -cp "libs/*" -d out -sourcepath src $(find src -name "*.java")
```
- `libs/*` refers to the folder containing required JAR dependencies (MongoDB driver, etc.)
- `src` contains your Java source files
- `out` is the output directory for compiled `.class` files

## Run

Run the compiled application:
```bash
java -cp "libs/*:out" com.jobportal.Main
```
- Make sure MongoDB is running before starting the backend
- Adjust the classpath separator (`:` for Linux/macOS, `;` for Windows) accordingly

## API Endpoints

Typical endpoints exposed (example):
- `POST /auth/login` — User login
- `POST /auth/register` — User registration
- `GET /jobs` — List jobs
- `POST /jobs` — Create job (employers only)
- `GET /jobs/{id}` — Job details
- `POST /applications` — Apply to a job
- `GET /applications` — View user's applications
*(Refer to API docs or code comments for full endpoint details)*

## Troubleshooting

- If backend fails to connect to MongoDB, check that the container or local instance is running and accessible
- Verify your ConnectionStrings are correct and matches your MongoDB deployment
- Make sure all dependencies (JARs) are present in the libs folder
- Check Java version compatibility

## Project Structure

```
/
├── libs                                # External JAR libraries and dependencies
│   ├── bson-4.11.0.jar                 # BSON format support for MongoDB driver
│   ├── gson-2.10.1.jar                 # JSON serialization/deserialization library
│   ├── mongodb-driver-core-4.11.0.jar  # Core MongoDB driver functionality
│   ├── mongodb-driver-sync-4.11.0.jar  # Synchronous MongoDB driver API
│   ├── nanohttpd-2.3.1.jar             # Lightweight HTTP server library
│   └── slf4j-nop-2.0.9.jar             # SLF4J no-operation logger implementation
├── README.md                           # Project documentation and overview
└── src                                 # Source code directory
    └── com
        └── jobportal                   # Main Java package for the backend
            ├── api                     # API related classes (HTTP server, routing)
            │   ├── HttpServer.java     # Main HTTP server setup and entry point for requests
            │   └── routes              # Sub-package for route handlers (endpoints)
            ├── domain                  # Domain model classes representing entities
            ├── Main.java               # Application entry point with main() method
            └── repo                    # Repository classes handling DB access logic
```
