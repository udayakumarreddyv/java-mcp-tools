package org.springframework.ai.mcp.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService();
    }

    @Test
    void testGetWeatherForecastByLocation() {
        double latitude = 47.6062;
        double longitude = -122.3321;

        String forecast = weatherService.getWeatherForecastByLocation(latitude, longitude);
        assertNotNull(forecast);
        assertTrue(forecast.contains("Temperature:"));
        assertTrue(forecast.contains("Wind:"));
    }

    @Test
    void testGetAlerts() {
        String state = "NY";

        String alerts = weatherService.getAlerts(state);
        assertNotNull(alerts);
    }
}
