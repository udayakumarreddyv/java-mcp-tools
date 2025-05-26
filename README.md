# Java MCP Tools

A Spring Boot application demonstrating Model Context Protocol (MCP) server implementation with weather forecasting and courier tracking services.

## Architecture Documentation

The project's architecture is documented through several Mermaid diagrams:

- [Class Diagram](docs/diagrams/class-diagram.md) - System components and their relationships
- [Weather Service Flow](docs/diagrams/weather-sequence.md) - Weather forecast request flow
- [Courier Service Flow](docs/diagrams/courier-sequence.md) - Package tracking flow

See the [diagrams documentation](docs/diagrams/README.md) for more details.

## Project Structure
```
.
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── org/springframework/ai/mcp/server/
        │       ├── McpServerApplication.java
        │       └── service/
        │           ├── CourierService.java
        │           └── WeatherService.java
        └── resources/
            └── application.properties
```

## Features

### Weather Service
- Fetch weather forecasts using latitude/longitude coordinates
- Get weather alerts by US state code
- Uses the National Weather Service API
- Provides detailed forecast information including:
  - Temperature
  - Wind conditions
  - Detailed forecast descriptions
  - Weather alerts and severity

### Courier Service
- Track packages for multiple carriers (FedEx, UPS)
- Get real-time package location
- Retrieve estimated delivery times
- Check delivery status

## Prerequisites

- Java Development Kit (JDK)
- Maven
- Spring Boot 3.3.6
- Spring AI BOM 1.0.0-SNAPSHOT

## Building the Project

1. Make sure you have Maven and JDK installed:
```powershell
mvn --version
java --version
```

2. Build the application using Maven:
```powershell
mvn clean install
```

The build will create a JAR file at `target/java-mcp-tools-0.0.1-SNAPSHOT.jar`

## Running the Application

1. Using Maven (recommended for development):
```powershell
mvn spring-boot:run
```

2. Or using the generated JAR:
```powershell
java -jar .\target\java-mcp-tools-0.0.1-SNAPSHOT.jar
```

## Running the Server

### SSE (Server-Sent Events) Mode

By default, the server runs in SSE mode, which is suitable for web-based applications. This mode enables real-time communication between the server and clients through HTTP endpoints.

To run in SSE mode:
```powershell
mvn spring-boot:run
```

The server will start with web support enabled and listen for incoming HTTP connections.

### STDIO Mode

STDIO mode is useful for command-line applications and direct process communication. To enable STDIO mode:

1. Modify `application.properties`:
```properties
spring.main.web-application-type=none
spring.main.banner-mode=off
spring.ai.mcp.server.stdio=true
```

2. Run the application:
```powershell
java -jar .\target\java-mcp-tools-0.0.1-SNAPSHOT.jar
```

Note: In STDIO mode, console logging should be disabled to prevent interference with the STDIO transport.

## Configuration

The application can be configured through `application.properties`:

```properties
spring.ai.mcp.server.name=java-mcp-tools
spring.ai.mcp.server.version=0.0.1
```

## API Examples

### Weather Service

1. Get Weather Forecast:
```java
weatherService.getWeatherForecastByLocation(47.6062, -122.3321);
```

2. Get Weather Alerts:
```java
weatherService.getAlerts("NY");
```

### Courier Service

1. Track Package Location:
```java
courierService.getCurrentLocation("FEDEX", "123456789");
```

2. Get Estimated Delivery:
```java
courierService.getEstimatedDelivery("UPS", "987654321");
```

## Available Tools

The server provides the following tools through the Model Context Protocol:

### Weather Tools
- `getWeatherForecastByLocation`: Get weather forecast for specific coordinates
  - Parameters: latitude (double), longitude (double)
  - Returns detailed forecast including temperature, wind conditions, and descriptions

- `getAlerts`: Get weather alerts for a US state
  - Parameter: state (Two-letter US state code)
  - Returns active weather alerts with severity and instructions

### Courier Tools
- `getCurrentLocation`: Get package current location
  - Parameters: carrier (FEDEX/UPS), trackingNumber
  - Returns current location information

- `getEstimatedDelivery`: Get package delivery estimate
  - Parameters: carrier (FEDEX/UPS), trackingNumber
  - Returns estimated delivery date/time

- `getDeliveryStatus`: Get package delivery status
  - Parameters: carrier (FEDEX/UPS), trackingNumber
  - Returns current delivery status

## Development

### Key Components

1. `McpServerApplication.java`: Main application class with Spring Boot configuration
   - Configures weather and courier tool callbacks
   - Sets up MCP server beans

2. `WeatherService.java`: Weather forecast and alerts service
   - Uses National Weather Service API
   - Provides latitude/longitude based forecasts
   - State-based weather alerts

3. `CourierService.java`: Package tracking service
   - Supports FedEx and UPS
   - Mock implementation for demonstration
   - Tracking and delivery status features

### Dependencies

The project uses:
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.6</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
    </dependency>
</dependencies>
```

## MCP Boot Starter Clients

The project uses the Spring AI Boot Starter for MCP Server:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
</dependency>
```

This starter provides:
- Automatic configuration of MCP server endpoints
- Integration with Spring Boot's auto-configuration
- Support for both SSE and STDIO transport modes
- Built-in tool callback registration
- Integration with Spring's dependency injection

To create a client application, use the corresponding client starter:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-client</artifactId>
</dependency>
```

## Connecting MCP Clients

Clients can connect to the MCP server using either SSE or STDIO mode, depending on their requirements.

### SSE Mode Connection

For web-based clients using SSE mode:

1. Add the MCP client dependency to your project:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-client</artifactId>
</dependency>
```

2. Configure the client in your application.properties:
```properties
spring.ai.mcp.client.transport=sse
spring.ai.mcp.client.base-url=http://localhost:8080
```

3. Use the client in your application:
```java
@Autowired
private McpClient mcpClient;

// Call weather service
String forecast = mcpClient.call("getWeatherForecastByLocation", 
    Map.of("latitude", 47.6062, "longitude", -122.3321));

// Call courier service
String tracking = mcpClient.call("getCurrentLocation", 
    Map.of("carrier", "FEDEX", "trackingNumber", "123456789"));
```

### STDIO Mode Connection

For command-line clients using STDIO mode:

1. Configure your client application:
```properties
spring.ai.mcp.client.transport=stdio
spring.ai.mcp.client.process.command=java -jar path/to/java-mcp-tools.jar
```

2. Use the client with STDIO transport:
```java
@Autowired
private McpClient mcpClient;

// The client will automatically communicate through STDIO
String response = mcpClient.call("getAlerts", Map.of("state", "NY"));
```

## Contributing

We welcome contributions from the community! This project is open to all contributors who want to improve the MCP tools ecosystem.

### How to Contribute

1. Fork the Repository
2. Create your Feature Branch:
```powershell
git checkout -b feature/AmazingFeature
```

3. Make your Changes
   - Add new tools
   - Improve existing services
   - Fix bugs
   - Enhance documentation

4. Ensure Code Quality
   - Follow existing code style
   - Add appropriate comments
   - Update documentation
   - Write/update tests if applicable

5. Commit your Changes:
```powershell
git commit -m "Add: Brief description of your changes"
```

6. Push to your Branch:
```powershell
git push origin feature/AmazingFeature
```

7. Open a Pull Request

### Development Guidelines

- Use meaningful commit messages following conventional commits
- Keep changes focused and atomic
- Document new features or API changes
- Add tests for new functionality
- Update README.md if adding new features or changing configurations

### Adding New Tools

When adding new tools to the server:

1. Create a new service class under `src/main/java/org/springframework/ai/mcp/server/service`
2. Use the `@Tool` annotation to expose methods
3. Register the tool in `McpServerApplication.java`
4. Add documentation and examples
5. Include appropriate error handling

Example of a new tool:
```java
@Service
public class NewService {
    @Tool(description = "Description of what the tool does")
    public String newTool(String param) {
        // Implementation
    }
}

// In McpServerApplication.java
@Bean
public ToolCallbackProvider newTools(NewService newService) {
    return MethodToolCallbackProvider.builder()
        .toolObjects(newService)
        .build();
}
```

### Getting Help

- Open an issue for bugs or feature requests
- Use discussions for questions and community help
- Check existing issues and pull requests before creating new ones
