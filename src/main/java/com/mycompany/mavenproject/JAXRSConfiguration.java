package com.mycompany.mavenproject;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author methdul
 */
@ApplicationPath("/api/v1")
public class JAXRSConfiguration extends Application {
}
