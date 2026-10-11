package com.logistics.platform.service;

import com.logistics.platform.dto.CreateOrderDTO;
import com.logistics.platform.dto.OrderResponseDTO;
import com.logistics.platform.dto.UpdateOrderDTO;
import com.logistics.platform.entity.Order;
import com.logistics.platform.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Get all orders
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get order by ID
    public Optional<OrderResponseDTO> getOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // Get orders by status
    public List<OrderResponseDTO> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get orders by customer ID
    public List<OrderResponseDTO> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get orders by driver ID
    public List<OrderResponseDTO> getOrdersByDriverId(Integer driverId) {
        return orderRepository.findByAssignedDriverId(driverId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get orders by route ID
    public List<OrderResponseDTO> getOrdersByRouteId(Integer routeId) {
        return orderRepository.findByAssignedRouteId(routeId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get orders by priority
    public List<OrderResponseDTO> getOrdersByPriority(String priority) {
        return orderRepository.findByPriority(priority)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Create order
    public OrderResponseDTO createOrder(CreateOrderDTO request) {

        Order order = new Order();

        order.setCustomerId(request.getCustomerId());
        order.setPickupLatitude(request.getPickupLatitude());
        order.setPickupLongitude(request.getPickupLongitude());
        order.setDeliveryLatitude(request.getDeliveryLatitude());
        order.setDeliveryLongitude(request.getDeliveryLongitude());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPackageWeightKg(request.getPackageWeightKg());
        order.setPackageUnits(request.getPackageUnits());
        order.setTimeWindowStart(request.getTimeWindowStart());
        order.setTimeWindowEnd(request.getTimeWindowEnd());
        order.setPriority(request.getPriority());
        order.setDeliveryNotes(request.getDeliveryNotes());
        Order savedOrder = orderRepository.save(order);

        return toResponseDTO(savedOrder);
    }

    // Update order
    public OrderResponseDTO updateOrder(Integer id, UpdateOrderDTO request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (request.getDeliveryAddress() != null) {
            order.setDeliveryAddress(request.getDeliveryAddress());
        }

        if (request.getPackageWeightKg() != null) {
            order.setPackageWeightKg(request.getPackageWeightKg());
        }

        if (request.getPackageUnits() != null) {
            order.setPackageUnits(request.getPackageUnits());
        }

        if (request.getTimeWindowStart() != null) {
            order.setTimeWindowStart(request.getTimeWindowStart());
        }

        if (request.getTimeWindowEnd() != null) {
            order.setTimeWindowEnd(request.getTimeWindowEnd());
        }

        if (request.getPriority() != null) {
            order.setPriority(request.getPriority());
        }

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        if (request.getAssignedDriverId() != null) {
            order.setAssignedDriverId(request.getAssignedDriverId());
        }

        if (request.getAssignedRouteId() != null) {
            order.setAssignedRouteId(request.getAssignedRouteId());
        }

        if (request.getPickupLatitude() != null)
            order.setPickupLatitude(request.getPickupLatitude());

        if (request.getPickupLongitude() != null)
            order.setPickupLongitude(request.getPickupLongitude());

        if (request.getDeliveryLatitude() != null)
            order.setDeliveryLatitude(request.getDeliveryLatitude());

        if (request.getDeliveryLongitude() != null)
            order.setDeliveryLongitude(request.getDeliveryLongitude());

        if (request.getDeliveryNotes() != null) {
            order.setDeliveryNotes(request.getDeliveryNotes());
        }

        Order updatedOrder = orderRepository.save(order);

        return toResponseDTO(updatedOrder);
    }

    // Delete order
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private OrderResponseDTO toResponseDTO(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomerId());
        dto.setPickupLatitude(order.getPickupLatitude());
        dto.setPickupLongitude(order.getPickupLongitude());
        dto.setDeliveryLatitude(order.getDeliveryLatitude());
        dto.setDeliveryLongitude(order.getDeliveryLongitude());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setPackageWeightKg(order.getPackageWeightKg());
        dto.setPackageUnits(order.getPackageUnits());
        dto.setTimeWindowStart(order.getTimeWindowStart());
        dto.setTimeWindowEnd(order.getTimeWindowEnd());
        dto.setPriority(order.getPriority());
        dto.setStatus(order.getStatus());
        dto.setAssignedDriverId(order.getAssignedDriverId());
        dto.setAssignedRouteId(order.getAssignedRouteId());
        dto.setPickupTime(order.getPickupTime());
        dto.setActualDeliveryTime(order.getActualDeliveryTime());
        dto.setDeliveryNotes(order.getDeliveryNotes());
        return dto;
    }
}