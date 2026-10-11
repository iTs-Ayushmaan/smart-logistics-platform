package com.logistics.platform.controller;

import com.logistics.platform.dto.CreateOrderDTO;
import com.logistics.platform.dto.OrderResponseDTO;
import com.logistics.platform.dto.UpdateOrderDTO;
import com.logistics.platform.service.OrderService;
import jakarta.validation.Valid;
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

    // Get all orders
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Integer id) {

        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                orderService.getOrdersByStatus(status)
        );
    }

    // Get orders by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomerId(
            @PathVariable Integer customerId) {

        return ResponseEntity.ok(
                orderService.getOrdersByCustomerId(customerId)
        );
    }

    // Get orders by driver ID
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByDriverId(
            @PathVariable Integer driverId) {

        return ResponseEntity.ok(
                orderService.getOrdersByDriverId(driverId)
        );
    }

    // Get orders by route ID
    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByRouteId(
            @PathVariable Integer routeId) {

        return ResponseEntity.ok(
                orderService.getOrdersByRouteId(routeId)
        );
    }

    // Get orders by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByPriority(
            @PathVariable String priority) {

        return ResponseEntity.ok(
                orderService.getOrdersByPriority(priority)
        );
    }

    // Create order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody CreateOrderDTO request) {

        return ResponseEntity.ok(
                orderService.createOrder(request)
        );
    }

    // Update order
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateOrderDTO request) {

        return ResponseEntity.ok(
                orderService.updateOrder(id, request)
        );
    }

    // Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Integer id) {

        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}