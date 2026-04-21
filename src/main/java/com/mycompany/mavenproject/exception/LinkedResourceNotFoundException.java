package com.mycompany.mavenproject.exception;

/**
 * Thrown when a sensor is registered with a room ID that does not exist.
 * Ensures data integrity between rooms and sensors.
 * 
 * @author methdul
 */
public class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String message) {
        super(message);
    }
}
