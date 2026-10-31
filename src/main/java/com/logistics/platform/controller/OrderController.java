package com.logistics.platform.controller;

import com.logistics.platform.dto.CreateOrderDTO;
import com.logistics.platform.dto.OrderResponseDTO;
import com.logistics.platform.dto.UpdateOrderDTO;
import com.logistics.platform.dto.UpdateOrderStatusDTO;
import com.logistics.platform.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ================= CUSTOMER =================

    // Create a new order.
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody CreateOrderDTO request,
            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(request, authentication));
    }

    // Get orders belonging to the logged-in customer.
    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                orderService.getMyOrders(authentication)
        );
    }

    // ================= DRIVER =================

    // Get orders assigned to the logged-in driver.
    @GetMapping("/my-deliveries")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<OrderResponseDTO>> getMyDeliveries(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                orderService.getMyDeliveries(authentication)
        );
    }

    // Update progress on an assigned order.
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<OrderResponseDTO> updateStatusAsDriver(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateOrderStatusDTO request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                orderService.updateStatusAsDriver(
                        id,
                        request,
                        authentication
                )
        );
    }

    // ================= SHARED ORDER VIEW =================

    // ADMIN: any order
    // CUSTOMER: own order
    // DRIVER: assigned order
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                orderService.getOrderById(id, authentication)
        );
    }

    // ================= ADMIN =================

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByStatus(status)
        );
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomerId(
            @PathVariable Integer customerId
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByCustomerId(customerId)
        );
    }

    @GetMapping("/driver/{driverId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByDriverId(
            @PathVariable Integer driverId
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByDriverId(driverId)
        );
    }

    @GetMapping("/route/{routeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByRouteId(
            @PathVariable Integer routeId
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByRouteId(routeId)
        );
    }

    @GetMapping("/priority/{priority}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByPriority(
            @PathVariable String priority
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByPriority(priority)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateOrderDTO request
    ) {
        return ResponseEntity.ok(
                orderService.updateOrder(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Integer id
    ) {
        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}