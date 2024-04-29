package com.example.licious.inventoryDemo.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrometheusController {
    private final MeterRegistry meterRegistry;

    public PrometheusController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Operation(summary = "Endpoint to view custom metrics.")
    @GetMapping("/metrics")
    public ResponseEntity<String> metrics() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(meterRegistry.toString());
    }
}
