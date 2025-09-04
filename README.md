# Job Portal
A full-stack job portal application with a React Native mobile frontend and a Java backend. The platform allows job seekers to browse and apply for jobs, manage applications, and keep profiles updated, while the backend manages authentication, job listings, applications, and employer data.

## Features

### Frontend 
- RESTful API endpoints for jobs, users, profiles, and applications
- Session based user authentication and authorization
- MongoDB database integration for data persistence
- Dockerized MongoDB setup for easy development and testing
- Configurable connection to local or remote MongoDB instances

### Backend
- RESTful endpoints for jobs, users, profiles, and applications 
- Session-based authentication and authorization 
- MongoDB database integration
- Dockerized MongoDB setup for development
- Configurable connection to local or remote MongoDB

## Tech Stack
- **Mobile** React Native (JavaScript), Expo
- **State Management:** Context API
- **Backend:** Java REST API
- **Database:** MongoDB (Dockerized or local)
- **Storage:** SecureStorage
- **Libraries:** MongoDB Java Driver, JSON parsing libraries

### Light Theme
Click to view:
- [Auth Page](./react-frontend/screenshots/light-theme/auth-page.png)
- [Home Page](./react-frontend/screenshots/light-theme/home-page.png)
- [Job Page](./react-frontend/screenshots/light-theme/job-page1.png)
- [Job Page Continued 1](./react-frontend/screenshots/light-theme/job-page2.png)
- [Job Page Continued 2](./react-frontend/screenshots/light-theme/job-page3.png)
- [Apply Page](./react-frontend/screenshots/light-theme/apply-page.png)
- [Profile Page](./react-frontend/screenshots/light-theme/profile-page.png)
- [View Resume Page](./react-frontend/screenshots/light-theme/view-resume-page.png)
- [Edit Contact Page](./react-frontend/screenshots/light-theme/edit-contact-page.png)

## Prerequisites

### Frontend
- Node.js v16 or higher
- React Native development environment set up (Android Studio/Xcode)
- Expo CLI
- Backend API endpoint (configured separately)

### Backend
- Java Development Kit (JDK) 11 or higher
- Docker (for running MongoDB container)
- MongoDB (optional, if you want to run local instance)
- Internet connection for downloading dependencies

## Setup

### Frontend
1. Navigate to the `react-frontend` directory:
    ```bash
    cd Job-Portal-App
    ```
2. Install dependencies:
    ```bash
    npm install
    ```
3. Create .env in Job-Portal-App/ with:
    ```bash
    BACKEND_API_URL=http://localhost:7000
    ```
4. Start the app:
    ```bash
    npx expo start -c
    ```
5. Scan the QR code with Expo Go or run on emulator.

### Backend

#### Using Docker (Recommended)
1. Start MongoDB container:
    ```bash
    docker run -d --name jobportal-mongo -p 27017:27017 mongo:latest
    ```
2. Ensure MongoDB is accessible on mongodb://localhost:27017.

#### Local MongoDB Instance (Optional)
1. Install MongoDB on your machine: [MongoDB Installation Guide](https://www.mongodb.com/docs/manual/installation/)
2. Start MongoDB service locally.
3. Update the connection string in your configuration file to:
```bash
mongodb://localhost:27017
```

#### Compile and Run Backend
1. Navigate to the `java-backend` directory:
    ```bash
    javac -cp "libs/*" -d out -sourcepath src $(find src -name "*.java")
    java -cp "libs/*:out" com.jobportal.Main
    ```
- Adjust classpath separator (: Linux/macOS, ; Windows) as needed.


## API Endpoints
- `POST /auth/login` — User login
- `POST /auth/register` — User registration
- `GET /jobs` — List jobs
- `POST /jobs` — Create job (employers only)
- `GET /jobs/{id}` — Job details
- `POST /applications` — Apply to a job
- `GET /applications` — View user's applications

## Troubleshooting

### Frontend
- Make sure your React Native environment is set up correctly (React Native docs)
- For Android, ensure emulator/device is running and connected
- Check API URLs and environment variables carefully
- Clear cache if you encounter build or runtime issues:
```bash
npm start --reset-cache
```

### Backend
- If backend fails to connect to MongoDB, check that the container or local instance is running and accessible
- Verify your ConnectionStrings are correct and matches your MongoDB deployment
- Make sure all dependencies (JARs) are present in the libs folder
- Check Java version compatibility

## Project Structure
```
Frontend
├── app.config.js                   # Expo app configuration file (app settings, build info)
├── app.json                        # Expo configuration file (app metadata, environment)
├── babel.config.js                 # Babel config for JavaScript/React Native transpiling
├── jsconfig.json                   # JS/TS config for editor tooling and path aliases
├── package.json                    # Project metadata, dependencies, and scripts
├── package-lock.json               # Auto-generated lock file to ensure consistent installs
├── README.md                       # Project documentation and overview
├── src                             # Source code folder
│   ├── App.js                      # Main entry point of the React Native app
│   ├── assets                      # Static assets like images and icons
│   │   ├── adaptive-icon.png       # Adaptive app icon for different devices
│   │   ├── favicon.png             # Favicon (used for web or development tools)
│   │   ├── icon.png                # Main app icon
│   │   └── splash-icon.png         # Splash screen icon shown on app launch
│   ├── components                  # Reusable UI components (buttons, cards, etc.)
│   ├── contexts                    # React Contexts for global state management
│   │   ├── AuthContext.js          # Context object for authentication state
│   │   └── AuthProvider.jsx        # Provider component wrapping app for auth context
│   ├── index.js                    # Likely the React Native app entry point for bundler
│   ├── navigation                  # React Navigation setup and route stacks
│   │   ├── AppNavigator.jsx        # Root navigator combining all stacks
│   │   ├── ProtectedStack.jsx      # Navigation stack for authenticated users
│   │   └── PublicStack.jsx         # Navigation stack for unauthenticated users
│   ├── screens                     # Screen components representing app pages
│   ├── services                    # API calls and backend interaction logic
│   └── utils                       # Utility functions/helpers
│       └── axiosInstance.js        # Axios instance configured for API requests
└── theme-colors.txt                # Possibly a reference file for app color palette/themes

Backend
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
