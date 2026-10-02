package com.logistics.platform.controller;

import com.logistics.platform.entity.RouteOrder;
import com.logistics.platform.service.RouteOrderService;
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

    @GetMapping
    public List<RouteOrder> getAllRouteOrders() {
        return routeOrderService.getAllRouteOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteOrder> getRouteOrderById(
            @PathVariable Integer id) {

        return routeOrderService.getRouteOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/route/{routeId}")
    public List<RouteOrder> getRouteOrdersByRouteId(
            @PathVariable Integer routeId) {

        return routeOrderService.getRouteOrdersByRouteId(routeId);
    }

    @GetMapping("/order/{orderId}")
    public List<RouteOrder> getRouteOrdersByOrderId(
            @PathVariable Integer orderId) {

        return routeOrderService.getRouteOrdersByOrderId(orderId);
    }

    @GetMapping("/status/{status}")
    public List<RouteOrder> getRouteOrdersByStatus(
            @PathVariable String status) {

        return routeOrderService.getRouteOrdersByStatus(status);
    }

    @PostMapping
    public RouteOrder createRouteOrder(
            @RequestBody RouteOrder routeOrder) {

        return routeOrderService.createRouteOrder(routeOrder);
    }

    @PutMapping
    public RouteOrder updateRouteOrder(
            @RequestBody RouteOrder routeOrder) {

        return routeOrderService.updateRouteOrder(routeOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRouteOrder(
            @PathVariable Integer id) {

        routeOrderService.deleteRouteOrder(id);
        return ResponseEntity.noContent().build();
    }
}