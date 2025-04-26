package org.sk_softech.cabbookuser.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/driver-location")
public class locationGetter {

    // 🔁 To hold the latest location received from Kafka
    private final AtomicReference<String> latestLocation = new AtomicReference<>("{\"lat\":21.0,\"lng\":72.0}");

    // 1️⃣ Kafka Listener: gets driver location (in format: "lat,lng")
    @KafkaListener(topics = "cab-location", groupId = "user-group")
    public void cabLocation(String location) {

        System.out.println("Received location: " + location);
        latestLocation.set(location);
    }

    // 2️⃣ REST API: frontend will poll this
    @GetMapping
    public Map<String, Double> getCurrentLocation() {
        try {
            String[] coords = latestLocation.get().split(",");
            double lat = Double.parseDouble(coords[0]);
            double lng = Double.parseDouble(coords[1]);

            return Map.of("lat", lat, "lng", lng);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("lat", 0.0, "lng", 0.0); // fallback
        }
    }

}
