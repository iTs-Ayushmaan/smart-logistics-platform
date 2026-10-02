package com.logistics.platform.controller;

import com.logistics.platform.entity.RouteDecision;
import com.logistics.platform.service.RouteDecisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route-decisions")
public class RouteDecisionController {

    private final RouteDecisionService routeDecisionService;

    public RouteDecisionController(RouteDecisionService routeDecisionService) {
        this.routeDecisionService = routeDecisionService;
    }

    @GetMapping
    public List<RouteDecision> getAllRouteDecisions() {
        return routeDecisionService.getAllRouteDecisions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDecision> getRouteDecisionById(
            @PathVariable Integer id) {

        return routeDecisionService.getRouteDecisionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/route/{routeId}")
    public List<RouteDecision> getRouteDecisionsByRouteId(
            @PathVariable Integer routeId) {

        return routeDecisionService.getRouteDecisionsByRouteId(routeId);
    }

    @GetMapping("/status/{decisionStatus}")
    public List<RouteDecision> getRouteDecisionsByStatus(
            @PathVariable String decisionStatus) {

        return routeDecisionService
                .getRouteDecisionsByStatus(decisionStatus);
    }

    @GetMapping("/approved-by/{approvedBy}")
    public List<RouteDecision> getRouteDecisionsByApprovedBy(
            @PathVariable Integer approvedBy) {

        return routeDecisionService
                .getRouteDecisionsByApprovedBy(approvedBy);
    }

    @PostMapping
    public RouteDecision createRouteDecision(
            @RequestBody RouteDecision routeDecision) {

        return routeDecisionService.createRouteDecision(routeDecision);
    }

    @PutMapping
    public RouteDecision updateRouteDecision(
            @RequestBody RouteDecision routeDecision) {

        return routeDecisionService.updateRouteDecision(routeDecision);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRouteDecision(
            @PathVariable Integer id) {

        routeDecisionService.deleteRouteDecision(id);
        return ResponseEntity.noContent().build();
    }
}