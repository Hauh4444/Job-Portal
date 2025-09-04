# Job Portal App

A mobile-first job portal application built with React Native, designed to connect job seekers with employers. Easily browse and apply for jobs, manage applications, and stay updated with job alerts through a smooth and intuitive mobile experience.

> **Status:** In Progress  
> Features and design are actively evolving.

## Features 

- User authentication with secure signup and login 
- Job listing and detailed job view 
- Responsive and accessible mobile UI 
- Profile view and editing 
- Resume upload and management 
- Contact information updating 
- Applying to jobs directly through the platform

## Tech Stack

- **Mobile:** React Native (JavaScript), Expo
- **State Management:** Context API
- **Backend:** REST API
- **Storage:** SecureStorage

## Screenshots

### Light Theme

Click to view:
- [Auth Page](./screenshots/light-theme/auth-page.png)
- [Home Page](./screenshots/light-theme/home-page.png)
- [Job Page](./screenshots/light-theme/job-page1.png)
- [Job Page Continued 1](./screenshots/light-theme/job-page2.png)
- [Job Page Continued 2](./screenshots/light-theme/job-page3.png)
- [Apply Page](./screenshots/light-theme/apply-page.png)
- [Profile Page](./screenshots/light-theme/profile-page.png)
- [View Resume Page](./screenshots/light-theme/view-resume-page.png)
- [Edit Contact Page](./screenshots/light-theme/edit-contact-page.png)

## Prerequisites

- Node.js v16 or higher
- React Native development environment set up (Android Studio/Xcode)
- Expo CLI
- Backend API endpoint (configured separately)

## Setup 

1. Navigate to the `Job-Portal-App` directory:
    ```bash
    cd Job-Portal-App
    ```
2. Install dependencies:
    ```bash
    npm install
    ```

## Environment Variables
    
Before running the app, create a `.env` file in `Job-Portal-App/` with:
```
BACKEND_API_URL=http://192.168.1.32:7000
```
- `BACKEND_API_URL` — The URL where your backend api is running

**Note:**
- Adjust `BACKEND_API_URL` if your backend runs on a different port or domain.

## Running the App

### With Expo

```bash
npx expo start -c
```

Scan the QR code with Expo Go app on your phone, or run on emulator.

## Troubleshooting

- Make sure your React Native environment is set up correctly (React Native docs)
- For Android, ensure emulator/device is running and connected
- Check API URLs and environment variables carefully
- Clear cache if you encounter build or runtime issues:
```bash
npm start --reset-cache
```

## Project Structure

```
/
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
```

