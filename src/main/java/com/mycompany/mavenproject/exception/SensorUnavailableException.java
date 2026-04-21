package com.mycompany.mavenproject.exception;

/**
 * Thrown when a data point is submitted to a sensor that is currently marked as unavailable or offline.
 * 
 * @author methdul
 */
public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String message) {
        super(message);
    }
}
