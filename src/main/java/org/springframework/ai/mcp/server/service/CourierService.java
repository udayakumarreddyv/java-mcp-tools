package org.springframework.ai.mcp.server.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CourierService {

    private static final String FEDEX_TRACKING_URL = "https://www.fedex.com/fedextrack/?trknbr=";
    private static final String UPS_TRACKING_URL = "https://www.ups.com/track?track=yes&trackNums=";

    @Tool(name = "get_current_location", description = "Get the current location of a courier package")
    public String getCurrentLocation(String carrier, String trackingNumber) {
        validateCarrier(carrier);
        // Note: This is a mock implementation since direct API access would require authentication
        return String.format("Package with tracking number %s is currently in Miami, FL", trackingNumber);
    }

    @Tool(name = "get_estimated_delivery", description = "Get the estimated delivery time of a package")
    public String getEstimatedDelivery(String carrier, String trackingNumber) {
        validateCarrier(carrier);
        // Mock implementation
        LocalDateTime estimatedDelivery = LocalDateTime.now().plusDays(2);
        return String.format("Estimated delivery for tracking number %s: %s", trackingNumber, estimatedDelivery);
    }

    @Tool(name = "get_delivery_status", description = "Get the current status of a delivery")
    public String getDeliveryStatus(String carrier, String trackingNumber) {
        validateCarrier(carrier);
        // Mock implementation
        return String.format("Package with tracking number %s is In Transit", trackingNumber);
    }    @SuppressWarnings("unused")
    private String getTrackingUrl(String carrier, String trackingNumber) {
        return switch (carrier.toUpperCase()) {
            case "FEDEX" -> FEDEX_TRACKING_URL + trackingNumber;
            case "UPS" -> UPS_TRACKING_URL + trackingNumber;
            default -> throw new IllegalArgumentException("Invalid carrier");
        };
    }

    private void validateCarrier(String carrier) {
        if (carrier == null || (!carrier.equalsIgnoreCase("FEDEX") && !carrier.equalsIgnoreCase("UPS"))) {
            throw new IllegalArgumentException("Invalid carrier. Supported carriers are FEDEX and UPS");
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TrackingResponse(
        @JsonProperty("status") String status,
        @JsonProperty("location") String location,
        @JsonProperty("estimatedDelivery") String estimatedDelivery
    ) {}
}
