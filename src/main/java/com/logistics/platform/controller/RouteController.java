package com.logistics.platform.controller;

import com.logistics.platform.entity.Route;
import com.logistics.platform.service.RouteService;
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

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Integer id) {
        return routeService.getRouteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/driver/{driverId}")
    public List<Route> getRoutesByDriverId(
            @PathVariable Integer driverId) {
        return routeService.getRoutesByDriverId(driverId);
    }

    @GetMapping("/date/{routeDate}")
    public List<Route> getRoutesByDate(
            @PathVariable LocalDate routeDate) {
        return routeService.getRoutesByDate(routeDate);
    }

    @GetMapping("/status/{status}")
    public List<Route> getRoutesByStatus(
            @PathVariable String status) {
        return routeService.getRoutesByStatus(status);
    }

    @PostMapping
    public Route createRoute(@RequestBody Route route) {
        return routeService.createRoute(route);
    }

    @PutMapping
    public Route updateRoute(@RequestBody Route route) {
        return routeService.updateRoute(route);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Integer id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}