package com.logistics.platform.controller;

import com.logistics.platform.entity.Order;
import com.logistics.platform.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersByCustomerId(
            @PathVariable Integer customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @GetMapping("/driver/{driverId}")
    public List<Order> getOrdersByDriverId(
            @PathVariable Integer driverId) {
        return orderService.getOrdersByDriverId(driverId);
    }

    @GetMapping("/route/{routeId}")
    public List<Order> getOrdersByRouteId(
            @PathVariable Integer routeId) {
        return orderService.getOrdersByRouteId(routeId);
    }

    @GetMapping("/priority/{priority}")
    public List<Order> getOrdersByPriority(
            @PathVariable String priority) {
        return orderService.getOrdersByPriority(priority);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}