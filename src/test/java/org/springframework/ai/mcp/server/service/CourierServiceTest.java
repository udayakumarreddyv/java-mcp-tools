package org.springframework.ai.mcp.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourierServiceTest {

    private CourierService courierService;

    @BeforeEach
    void setUp() {
        courierService = new CourierService();
    }

    @Test
    void testGetCurrentLocationWithValidFedEx() {
        String result = courierService.getCurrentLocation("FEDEX", "123456789");
        assertNotNull(result);
        assertTrue(result.contains("123456789"));
        assertTrue(result.contains("Miami"));
    }

    @Test
    void testGetCurrentLocationWithValidUPS() {
        String result = courierService.getCurrentLocation("UPS", "123456789");
        assertNotNull(result);
        assertTrue(result.contains("123456789"));
        assertTrue(result.contains("Miami"));
    }

    @Test
    void testGetEstimatedDeliveryWithValidCarrier() {
        String result = courierService.getEstimatedDelivery("FEDEX", "123456789");
        assertNotNull(result);
        assertTrue(result.contains("123456789"));
        assertTrue(result.contains("Estimated delivery"));
    }

    @Test
    void testGetDeliveryStatusWithValidCarrier() {
        String result = courierService.getDeliveryStatus("FEDEX", "123456789");
        assertNotNull(result);
        assertTrue(result.contains("123456789"));
        assertTrue(result.contains("In Transit"));
    }

    @Test
    void testInvalidCarrierThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            courierService.getCurrentLocation("INVALID", "123456789"));
        assertThrows(IllegalArgumentException.class, () -> 
            courierService.getEstimatedDelivery("INVALID", "123456789"));
        assertThrows(IllegalArgumentException.class, () -> 
            courierService.getDeliveryStatus("INVALID", "123456789"));
    }

    @Test
    void testNullCarrierThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            courierService.getCurrentLocation(null, "123456789"));
        assertThrows(IllegalArgumentException.class, () -> 
            courierService.getEstimatedDelivery(null, "123456789"));
        assertThrows(IllegalArgumentException.class, () -> 
            courierService.getDeliveryStatus(null, "123456789"));
    }
}
