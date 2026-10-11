package com.logistics.platform.controller;

import com.logistics.platform.dto.CreateRouteOrderDTO;
import com.logistics.platform.dto.RouteOrderResponseDTO;
import com.logistics.platform.dto.UpdateRouteOrderDTO;
import com.logistics.platform.service.RouteOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route-orders")
public class RouteOrderController {

    private final RouteOrderService routeOrderService;

    public RouteOrderController(RouteOrderService routeOrderService) {
        this.routeOrderService = routeOrderService;
    }

    // Get all route orders
    @GetMapping
    public ResponseEntity<List<RouteOrderResponseDTO>> getAllRouteOrders() {
        return ResponseEntity.ok(
                routeOrderService.getAllRouteOrders()
        );
    }

    // Get route order by ID
    @GetMapping("/{id}")
    public ResponseEntity<RouteOrderResponseDTO> getRouteOrderById(
            @PathVariable Integer id) {

        return routeOrderService.getRouteOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get route orders by route ID
    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<RouteOrderResponseDTO>> getRouteOrdersByRouteId(
            @PathVariable Integer routeId) {

        return ResponseEntity.ok(
                routeOrderService.getRouteOrdersByRouteId(routeId)
        );
    }

    // Get route orders by order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<RouteOrderResponseDTO>> getRouteOrdersByOrderId(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                routeOrderService.getRouteOrdersByOrderId(orderId)
        );
    }

    // Get route orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RouteOrderResponseDTO>> getRouteOrdersByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                routeOrderService.getRouteOrdersByStatus(status)
        );
    }

    // Create route order
    @PostMapping
    public ResponseEntity<RouteOrderResponseDTO> createRouteOrder(
            @Valid @RequestBody CreateRouteOrderDTO request) {

        return ResponseEntity.ok(
                routeOrderService.createRouteOrder(request)
        );
    }

    // Update route order
    @PutMapping("/{id}")
    public ResponseEntity<RouteOrderResponseDTO> updateRouteOrder(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateRouteOrderDTO request) {

        return ResponseEntity.ok(
                routeOrderService.updateRouteOrder(id, request)
        );
    }

    // Delete route order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRouteOrder(
            @PathVariable Integer id) {

        routeOrderService.deleteRouteOrder(id);

        return ResponseEntity.noContent().build();
    }
}