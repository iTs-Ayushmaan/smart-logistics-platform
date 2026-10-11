package com.logistics.platform.controller;

import com.logistics.platform.dto.CreateRouteDTO;
import com.logistics.platform.dto.RouteResponseDTO;
import com.logistics.platform.dto.UpdateRouteDTO;
import com.logistics.platform.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // Get all routes
    @GetMapping
    public ResponseEntity<List<RouteResponseDTO>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    // Get route by ID
    @GetMapping("/{id}")
    public ResponseEntity<RouteResponseDTO> getRouteById(
            @PathVariable Integer id) {

        return routeService.getRouteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get routes by driver ID
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<RouteResponseDTO>> getRoutesByDriverId(
            @PathVariable Integer driverId) {

        return ResponseEntity.ok(
                routeService.getRoutesByDriverId(driverId)
        );
    }

    // Get routes by date
    @GetMapping("/date/{routeDate}")
    public ResponseEntity<List<RouteResponseDTO>> getRoutesByDate(
            @PathVariable LocalDate routeDate) {

        return ResponseEntity.ok(
                routeService.getRoutesByDate(routeDate)
        );
    }

    // Get routes by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RouteResponseDTO>> getRoutesByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                routeService.getRoutesByStatus(status)
        );
    }

    // Create route
    @PostMapping
    public ResponseEntity<RouteResponseDTO> createRoute(
            @Valid @RequestBody CreateRouteDTO request) {

        return ResponseEntity.ok(
                routeService.createRoute(request)
        );
    }

    // Update route
    @PutMapping("/{id}")
    public ResponseEntity<RouteResponseDTO> updateRoute(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateRouteDTO request) {

        return ResponseEntity.ok(
                routeService.updateRoute(id, request)
        );
    }

    // Delete route
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(
            @PathVariable Integer id) {

        routeService.deleteRoute(id);

        return ResponseEntity.noContent().build();
    }
}