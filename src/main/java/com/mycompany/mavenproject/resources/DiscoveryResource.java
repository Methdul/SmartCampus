package com.mycompany.mavenproject.resources;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root discovery resource for the Smart Campus API.
 * Provides metadata and entry points for the service.
 * 
 * @author methdul
 */
@Path("/")
public class DiscoveryResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getApiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("api_title", "University Smart Campus REST Service");
        response.put("api_version", "1.0.0");
        response.put("administrator", "admin@smartcampus.ac.uk");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("rooms_collection", "/api/v1/rooms");
        endpoints.put("sensors_collection", "/api/v1/sensors");
        
        response.put("collections", endpoints);
        return response;
    }
}
