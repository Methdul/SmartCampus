package com.mycompany.mavenproject.resources;

import com.mycompany.mavenproject.exception.SensorUnavailableException;
import com.mycompany.mavenproject.model.Sensor;
import com.mycompany.mavenproject.model.SensorReading;
import com.mycompany.mavenproject.repository.DataStore;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Manages readings for a specific sensor context.
 * 
 * @author methdul
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * Retrieves all historical readings captured by this sensor.
     */
    @GET
    public List<SensorReading> getHistory() {
        return DataStore.readings.get(sensorId);
    }

    /**
     * Appends a new data reading.
     * Note: Sensor must be in ACTIVE status.
     */
    @POST
    public Response submitReading(SensorReading Reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        // Security check for maintenance mode
        if (sensor.getStatus() != null && sensor.getStatus().equalsIgnoreCase("MAINTENANCE")) {
            throw new SensorUnavailableException(
                    "Action Denied: Sensor [" + sensorId + "] is offline for maintenance."
            );
        }

        // Auto-generate metadata if missing
        if (Reading.getId() == null || Reading.getId().trim().isEmpty()) {
            Reading.setId(UUID.randomUUID().toString());
        }

        if (Reading.getTimestamp() == 0) {
            Reading.setTimestamp(System.currentTimeMillis());
        }

        DataStore.readings.get(sensorId).add(Reading);

        // Update the sensor's live value display
        sensor.setCurrentValue(Reading.getValue());

        return Response.created(URI.create("/api/v1/sensors/" + sensorId + "/readings/" + Reading.getId()))
                .entity(Reading)
                .build();
    }
}
