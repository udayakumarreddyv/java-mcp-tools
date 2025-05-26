package org.springframework.ai.mcp.server;

import org.junit.jupiter.api.Test;
import org.springframework.ai.mcp.server.service.CourierService;
import org.springframework.ai.mcp.server.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class McpServerApplicationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
    }

    @Test
    void weatherServiceBeanExists() {
        assertNotNull(context.getBean(WeatherService.class));
    }

    @Test
    void courierServiceBeanExists() {
        assertNotNull(context.getBean(CourierService.class));
    }

    @Test
    void testTextInputRecord() {
        String input = "test";
        McpServerApplication.TextInput textInput = new McpServerApplication.TextInput(input);
        assertEquals(input, textInput.input());
    }

    @Test
    void toUpperCaseToolExists() {
        assertNotNull(context.getBean("toUpperCase"));
    }
}
