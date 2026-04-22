# Smart Campus Sensor & Room Management API

## Overview

This project is a RESTful API developed using JAX-RS (Jersey) for managing a university Smart Campus system. It allows facilities managers and automated systems to manage rooms, sensors, and sensor readings across campus buildings.

The API follows REST principles, uses JSON for communication, and includes proper error handling with appropriate HTTP status codes. All data is stored in-memory using HashMap and ArrayList, as required by the coursework specification.

---

## Technologies Used

* Java 8
* JAX-RS (Jersey)
* Maven
* Apache Tomcat
* JSON
* Postman

---

## Project Structure

```
src/main/java/com/mycompany/mavenproject/

├── JAXRSConfiguration.java        # Application configuration (/api/v1)

├── model/
│   ├── Room.java
│   ├── Sensor.java
│   ├── SensorReading.java
│   └── ErrorResponse.java

├── repository/
│   └── DataStore.java             # In-memory storage (HashMap)

├── resources/
│   ├── DiscoveryResource.java
│   ├── RoomResource.java
│   ├── SensorResource.java
│   └── SensorReadingResource.java

├── exception/
│   ├── RoomNotEmptyException.java
│   ├── LinkedResourceNotFoundException.java
│   └── SensorUnavailableException.java

├── mapper/
│   ├── GlobalExceptionMapper.java
│   ├── RoomNotEmptyExceptionMapper.java
│   ├── LinkedResourceNotFoundExceptionMapper.java
│   └── SensorUnavailableExceptionMapper.java

└── filter/
    └── LoggingFilter.java
```

---

## How to Run the Project

### Prerequisites

* Java JDK 8 or above
* Apache Maven
* Apache Tomcat

### Steps

1. Open the project in NetBeans
2. Run the project using Tomcat
3. The application will deploy automatically

### Base URL

```
http://localhost:8080/mavenproject1/api/v1
```

---

## API Endpoints

### Discovery

GET /api/v1

---

### Rooms

GET /api/v1/rooms
POST /api/v1/rooms
GET /api/v1/rooms/{roomId}
DELETE /api/v1/rooms/{roomId}

---

### Sensors

GET /api/v1/sensors
GET /api/v1/sensors?type={type}
POST /api/v1/sensors
GET /api/v1/sensors/{sensorId}

---

### Sensor Readings (Sub-resource)

GET /api/v1/sensors/{sensorId}/readings
POST /api/v1/sensors/{sensorId}/readings

---

## Sample cURL Commands

### 1. Discovery

```bash
curl -X GET http://localhost:8080/mavenproject1/api/v1
```

### 2. Create Room

```bash
curl -X POST http://localhost:8080/mavenproject1/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LIB-301","name":"Library","capacity":50}'
```

### 3. Create Sensor

```bash
curl -X POST http://localhost:8080/mavenproject1/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"CO2-001","type":"CO2","status":"ACTIVE","currentValue":400.0,"roomId":"LIB-301"}'
```

### 4. Filter Sensors

```bash
curl -X GET "http://localhost:8080/mavenproject1/api/v1/sensors?type=CO2"
```

### 5. Add Sensor Reading

```bash
curl -X POST http://localhost:8080/mavenproject1/api/v1/sensors/CO2-001/readings \
-H "Content-Type: application/json" \
-d '{"value":420.5}'
```

---

## Error Handling

The API uses custom exception classes and exception mappers to return structured error responses.

| Scenario              | Exception                       | Status                    |
| --------------------- | ------------------------------- | ------------------------- |
| Room has sensors      | RoomNotEmptyException           | 409 Conflict              |
| Invalid roomId        | LinkedResourceNotFoundException | 422 Unprocessable Entity  |
| Sensor in maintenance | SensorUnavailableException      | 403 Forbidden             |
| Unexpected errors     | GlobalExceptionMapper           | 500 Internal Server Error |

---

## Example Error Response

```json
{
  "status": 409,
  "error": "Resource Conflict",
  "message": "Decommissioning failed: Room still contains active hardware sensors."
}
```

---

## Theory Answers

### Q1: JAX-RS Lifecycle

JAX-RS creates a new instance of a resource class for each request. In this project, data is stored in shared in-memory structures like HashMap and ArrayList, which persist across requests.

---

### Q2: HATEOAS

HATEOAS allows APIs to include links in responses. In this project, the `/api/v1` discovery endpoint provides links to resources like rooms and sensors.

---

### Q3: Returning IDs vs Full Objects

Returning full objects simplifies client usage, while returning only IDs reduces data size. This project returns full objects for simplicity.

---

### Q4: DELETE Idempotency

DELETE is idempotent because repeating the same request results in the same system state, even if the resource is already deleted.

---

### Q5: @Consumes Annotation

If a request is not JSON, JAX-RS returns a 415 Unsupported Media Type error.

---

### Q6: QueryParam vs PathParam

Query parameters are better for filtering because they allow flexible and scalable queries without changing the URL structure.

---

### Q7: Sub-resource Locator

The sub-resource locator is used for `/sensors/{sensorId}/readings`, allowing separation of logic into a dedicated class.

---

### Q8: HTTP 422 vs 404

422 is used when data is invalid (e.g., non-existent roomId), while 404 is used when the resource itself is missing.

---

### Q9: Stack Trace Security Risk

Exposing stack traces can reveal sensitive system details. This project prevents this using a global exception mapper.

---

### Q10: Logging Filters

A LoggingFilter is used to log request methods and response status codes, improving maintainability and consistency.
