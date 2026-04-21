package com.mycompany.mavenproject.exception;

/**
 * Thrown when an operation fails because a room still has active sensors.
 * 
 * @author methdul
 */
public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String message) {
        super(message);
    }
}
