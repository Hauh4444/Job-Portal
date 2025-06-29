package com.jobportal;

import com.jobportal.api.HttpServer;

public class Main {
    // Main entry point of the application
    public static void main(String[] args) {
        try {
            // Start the HTTP server (listens on port 8080)
            new HttpServer();
        } catch (Exception e) {
            // Print error if server fails to start
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
