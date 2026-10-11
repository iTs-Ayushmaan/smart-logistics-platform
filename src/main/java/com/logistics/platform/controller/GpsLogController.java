package com.logistics.platform.controller;

import com.logistics.platform.dto.CreateGpsLogDTO;
import com.logistics.platform.dto.GpsLogResponseDTO;
import com.logistics.platform.dto.UpdateGpsLogDTO;
import com.logistics.platform.service.GpsLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gps-logs")
public class GpsLogController {

    private final GpsLogService gpsLogService;

    public GpsLogController(GpsLogService gpsLogService) {
        this.gpsLogService = gpsLogService;
    }

    // Get all GPS logs
    @GetMapping
    public ResponseEntity<List<GpsLogResponseDTO>> getAllGpsLogs() {
        return ResponseEntity.ok(
                gpsLogService.getAllGpsLogs()
        );
    }

    // Get GPS log by ID
    @GetMapping("/{id}")
    public ResponseEntity<GpsLogResponseDTO> getGpsLogById(
            @PathVariable Long id) {

        return gpsLogService.getGpsLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get GPS logs by driver ID
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<GpsLogResponseDTO>> getGpsLogsByDriverId(
            @PathVariable Integer driverId) {

        return ResponseEntity.ok(
                gpsLogService.getGpsLogsByDriverId(driverId)
        );
    }

    // Get GPS logs by route ID
    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<GpsLogResponseDTO>> getGpsLogsByRouteId(
            @PathVariable Integer routeId) {

        return ResponseEntity.ok(
                gpsLogService.getGpsLogsByRouteId(routeId)
        );
    }

    // Get GPS logs by order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<GpsLogResponseDTO>> getGpsLogsByOrderId(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                gpsLogService.getGpsLogsByOrderId(orderId)
        );
    }

    // Create GPS log
    @PostMapping
    public ResponseEntity<GpsLogResponseDTO> createGpsLog(
            @Valid @RequestBody CreateGpsLogDTO request) {

        return ResponseEntity.ok(
                gpsLogService.createGpsLog(request)
        );
    }

    // Update GPS log
    @PutMapping("/{id}")
    public ResponseEntity<GpsLogResponseDTO> updateGpsLog(
            @PathVariable Long id,
            @Valid @RequestBody UpdateGpsLogDTO request) {

        return ResponseEntity.ok(
                gpsLogService.updateGpsLog(id, request)
        );
    }

    // Delete GPS log
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGpsLog(
            @PathVariable Long id) {

        gpsLogService.deleteGpsLog(id);

        return ResponseEntity.noContent().build();
    }
}