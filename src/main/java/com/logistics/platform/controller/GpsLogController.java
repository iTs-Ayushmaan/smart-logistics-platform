package com.logistics.platform.controller;

import com.logistics.platform.entity.GpsLog;
import com.logistics.platform.service.GpsLogService;
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

    @GetMapping
    public List<GpsLog> getAllGpsLogs() {
        return gpsLogService.getAllGpsLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GpsLog> getGpsLogById(@PathVariable Long id) {
        return gpsLogService.getGpsLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/driver/{driverId}")
    public List<GpsLog> getGpsLogsByDriverId(
            @PathVariable Integer driverId) {

        return gpsLogService.getGpsLogsByDriverId(driverId);
    }

    @GetMapping("/route/{routeId}")
    public List<GpsLog> getGpsLogsByRouteId(
            @PathVariable Integer routeId) {

        return gpsLogService.getGpsLogsByRouteId(routeId);
    }

    @GetMapping("/order/{orderId}")
    public List<GpsLog> getGpsLogsByOrderId(
            @PathVariable Integer orderId) {

        return gpsLogService.getGpsLogsByOrderId(orderId);
    }

    @PostMapping
    public GpsLog createGpsLog(@RequestBody GpsLog gpsLog) {
        return gpsLogService.createGpsLog(gpsLog);
    }

    @PutMapping
    public GpsLog updateGpsLog(@RequestBody GpsLog gpsLog) {
        return gpsLogService.updateGpsLog(gpsLog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGpsLog(@PathVariable Long id) {
        gpsLogService.deleteGpsLog(id);
        return ResponseEntity.noContent().build();
    }
}