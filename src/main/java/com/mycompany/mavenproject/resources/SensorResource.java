package com.mycompany.mavenproject.resources;

import com.mycompany.mavenproject.exception.LinkedResourceNotFoundException;
import com.mycompany.mavenproject.model.Sensor;
import com.mycompany.mavenproject.model.Room;
import com.mycompany.mavenproject.repository.DataStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Controller for managing IoT sensors deployed across the campus.
 * 
 * @author methdul
 */
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    /**
     * Retrieves sensors, optionally filtered by their functional type.
     */
    @GET
    public Collection<Sensor> listSensors(@QueryParam("type") String targetType) {
        if (targetType == null || targetType.trim().isEmpty()) {
            return DataStore.sensors.values();
        }

        return DataStore.sensors.values().stream()
                .filter(s -> s.getType() != null && s.getType().equalsIgnoreCase(targetType))
                .collect(Collectors.toList());
    }

    /**
     * Registers a new sensor and links it to a specific room.
     */
    @POST
    public Response registerSensor(Sensor sensor) {
        Room parentRoom = DataStore.rooms.get(sensor.getRoomId());

        if (parentRoom == null) {
            throw new LinkedResourceNotFoundException(
                    "Dependency Error: Room ID [" + sensor.getRoomId() + "] does not exist in our infrastructure."
            );
        }

        // Persist the sensor
        DataStore.sensors.put(sensor.getId(), sensor);
        parentRoom.getSensorIds().add(sensor.getId());
        
        // Initialize empty reading history
        DataStore.readings.put(sensor.getId(), new ArrayList<>());

        return Response.created(URI.create("/api/v1/sensors/" + sensor.getId()))
                .entity(sensor)
                .build();
    }

    /**
     * Retrieves specific sensor metadata.
     */
    @GET
    @Path("/{sensorId}")
    public Sensor getSensorDetails(@PathParam("sensorId") String sensorId) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor [" + sensorId + "] could not be found.");
        }

        return sensor;
    }

    /**
     * Sub-resource locator for sensor readings.
     */
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingSubResource(@PathParam("sensorId") String sensorId) {
        if (!DataStore.sensors.containsKey(sensorId)) {
            throw new NotFoundException("Sensor [" + sensorId + "] does not exist.");
        }

        return new SensorReadingResource(sensorId);
    }
}
