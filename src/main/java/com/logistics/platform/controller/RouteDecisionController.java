package com.logistics.platform.controller;

import com.logistics.platform.dto.CreateRouteDecisionDTO;
import com.logistics.platform.dto.RouteDecisionResponseDTO;
import com.logistics.platform.dto.UpdateRouteDecisionDTO;
import com.logistics.platform.service.RouteDecisionService;
import jakarta.validation.Valid;
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

    // Get all route decisions
    @GetMapping
    public ResponseEntity<List<RouteDecisionResponseDTO>> getAllRouteDecisions() {
        return ResponseEntity.ok(
                routeDecisionService.getAllRouteDecisions()
        );
    }

    // Get route decision by ID
    @GetMapping("/{id}")
    public ResponseEntity<RouteDecisionResponseDTO> getRouteDecisionById(
            @PathVariable Integer id) {

        return routeDecisionService.getRouteDecisionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get decisions by route ID
    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<RouteDecisionResponseDTO>> getRouteDecisionsByRouteId(
            @PathVariable Integer routeId) {

        return ResponseEntity.ok(
                routeDecisionService.getRouteDecisionsByRouteId(routeId)
        );
    }

    // Get decisions by status
    @GetMapping("/status/{decisionStatus}")
    public ResponseEntity<List<RouteDecisionResponseDTO>> getRouteDecisionsByStatus(
            @PathVariable String decisionStatus) {

        return ResponseEntity.ok(
                routeDecisionService.getRouteDecisionsByStatus(decisionStatus)
        );
    }

    // Get decisions by approver
    @GetMapping("/approved-by/{approvedBy}")
    public ResponseEntity<List<RouteDecisionResponseDTO>> getRouteDecisionsByApprovedBy(
            @PathVariable Integer approvedBy) {

        return ResponseEntity.ok(
                routeDecisionService.getRouteDecisionsByApprovedBy(approvedBy)
        );
    }

    // Create route decision
    @PostMapping
    public ResponseEntity<RouteDecisionResponseDTO> createRouteDecision(
            @Valid @RequestBody CreateRouteDecisionDTO request) {

        return ResponseEntity.ok(
                routeDecisionService.createRouteDecision(request)
        );
    }

    // Update route decision
    @PutMapping("/{id}")
    public ResponseEntity<RouteDecisionResponseDTO> updateRouteDecision(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateRouteDecisionDTO request) {

        return ResponseEntity.ok(
                routeDecisionService.updateRouteDecision(id, request)
        );
    }

    // Delete route decision
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRouteDecision(
            @PathVariable Integer id) {

        routeDecisionService.deleteRouteDecision(id);

        return ResponseEntity.noContent().build();
    }
}