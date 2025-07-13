package com.jobportal;

import com.jobportal.api.HttpServer;

public class Main {
    // Application entry point
    public static void main(String[] args) {
        try {
            new HttpServer();
        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
