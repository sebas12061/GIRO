package com.giro.backend.api;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("service", "giro-backend", "status", "UP");
    }
}
