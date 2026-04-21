package com.mycompany.mavenproject.repository;

import com.mycompany.mavenproject.model.Room;
import com.mycompany.mavenproject.model.Sensor;
import com.mycompany.mavenproject.model.SensorReading;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global application state acting as an in-memory repository.
 * Stores information regarding rooms, sensors, and historical readings.
 * 
 * @author methdul
 */
public class DataStore {
    
    // In-memory collections to replace a traditional database
    public static final Map<String, Room> rooms = new HashMap<>();
    public static final Map<String, Sensor> sensors = new HashMap<>();
    public static final Map<String, List<SensorReading>> readings = new HashMap<>();

    static {
        // Initializing the system with sample data for testing
        Room northLab = new Room("LIB-301", "Library Quiet Study", 50);
        rooms.put(northLab.getId(), northLab);

        Sensor tempSensor = new Sensor("TEMP-001", "Temperature", "ACTIVE", 22.4, "LIB-301");
        sensors.put(tempSensor.getId(), tempSensor);
        northLab.getSensorIds().add(tempSensor.getId());

        // Initialize empty readings for the default sensor
        readings.put(tempSensor.getId(), new ArrayList<>());
    }

    private DataStore() {
        // Private constructor to prevent instantiation
    }
}
