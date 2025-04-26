package org.sk_softech.cabbookdriver.controller;


import org.sk_softech.cabbookdriver.service.cabLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/driver-location")

public class locationSenderController {

    @Autowired
    private cabLocationService cabLocationService;

    @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        @PostMapping("/update")
        public ResponseEntity<?> updateLocation(@RequestBody Map<String, Object> payload) {
            String lat = payload.get("lat").toString();
            String lng = payload.get("lng").toString();

            String message = lat + "," + lng;
            System.out.println(message);
            cabLocationService.updateLocation(message);
            kafkaTemplate.send("driver-location", message);
            return ResponseEntity.ok(Map.of("message", "Location sent to Kafka"));
        }
}
