package com.mycompany.mavenproject.resources;

import com.mycompany.mavenproject.exception.RoomNotEmptyException;
import com.mycompany.mavenproject.model.Room;
import com.mycompany.mavenproject.repository.DataStore;
import java.net.URI;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Endpoint for managing campus rooms.
 * 
 * @author methdul
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    /**
     * Retrieves all rooms currently registered in the system.
     */
    @GET
    public Collection<Room> listAllRooms() {
        return DataStore.rooms.values();
    }

    /**
     * Adds a new room to the campus records.
     */
    @POST
    public Response registerRoom(Room room) {
        DataStore.rooms.put(room.getId(), room);
        return Response.created(URI.create("/api/v1/rooms/" + room.getId()))
                .entity(room)
                .build();
    }

    /**
     * Fetches details for a specific room by its ID.
     */
    @GET
    @Path("/{roomId}")
    public Room findRoomById(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);
        if (room == null) {
            throw new NotFoundException("Room [" + roomId + "] not found.");
        }
        return room;
    }

    /**
     * Removes a room from the system. 
     * Requirement: Room must not have any associated sensors.
     */
    @DELETE
    @Path("/{roomId}")
    public Response removeRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            throw new NotFoundException("Room [" + roomId + "] not found.");
        }

        // Safety check to prevent orphans
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Decommissioning failed: Room still contains active hardware sensors.");
        }

        DataStore.rooms.remove(roomId);
        return Response.ok()
                .entity("Room " + roomId + " has been successfully decommissioned.")
                .build();
    }
}
