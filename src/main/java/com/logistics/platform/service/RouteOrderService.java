package com.logistics.platform.service;

import com.logistics.platform.dto.CreateRouteOrderDTO;
import com.logistics.platform.dto.RouteOrderResponseDTO;
import com.logistics.platform.dto.UpdateRouteOrderDTO;
import com.logistics.platform.entity.RouteOrder;
import com.logistics.platform.repository.RouteOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteOrderService {

    private final RouteOrderRepository routeOrderRepository;

    public RouteOrderService(RouteOrderRepository routeOrderRepository) {
        this.routeOrderRepository = routeOrderRepository;
    }

    // Get all route orders
    public List<RouteOrderResponseDTO> getAllRouteOrders() {
        return routeOrderRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get route order by ID
    public Optional<RouteOrderResponseDTO> getRouteOrderById(Integer id) {
        return routeOrderRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // Get route orders by route ID
    public List<RouteOrderResponseDTO> getRouteOrdersByRouteId(Integer routeId) {
        return routeOrderRepository.findByRouteId(routeId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get route orders by order ID
    public List<RouteOrderResponseDTO> getRouteOrdersByOrderId(Integer orderId) {
        return routeOrderRepository.findByOrderId(orderId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get route orders by status
    public List<RouteOrderResponseDTO> getRouteOrdersByStatus(String status) {
        return routeOrderRepository.findByStatus(status)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Create route order
    public RouteOrderResponseDTO createRouteOrder(CreateRouteOrderDTO request) {

        RouteOrder routeOrder = new RouteOrder();

        routeOrder.setRouteId(request.getRouteId());
        routeOrder.setOrderId(request.getOrderId());
        routeOrder.setSequenceNumber(request.getSequenceNumber());
        routeOrder.setStatus(request.getStatus());

        RouteOrder savedRouteOrder = routeOrderRepository.save(routeOrder);

        return toResponseDTO(savedRouteOrder);
    }

    // Update route order
    public RouteOrderResponseDTO updateRouteOrder(
            Integer id,
            UpdateRouteOrderDTO request) {

        RouteOrder routeOrder = routeOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route order not found"));

        if (request.getSequenceNumber() != null) {
            routeOrder.setSequenceNumber(request.getSequenceNumber());
        }

        if (request.getStatus() != null) {
            routeOrder.setStatus(request.getStatus());
        }

        RouteOrder updatedRouteOrder = routeOrderRepository.save(routeOrder);

        return toResponseDTO(updatedRouteOrder);
    }

    // Delete route order
    public void deleteRouteOrder(Integer id) {
        routeOrderRepository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private RouteOrderResponseDTO toResponseDTO(RouteOrder routeOrder) {

        RouteOrderResponseDTO dto = new RouteOrderResponseDTO();

        dto.setId(routeOrder.getId());
        dto.setRouteId(routeOrder.getRouteId());
        dto.setOrderId(routeOrder.getOrderId());
        dto.setSequenceNumber(routeOrder.getSequenceNumber());
        dto.setEstimatedArrivalTime(routeOrder.getEstimatedArrivalTime());
        dto.setActualArrivalTime(routeOrder.getActualArrivalTime());
        dto.setEstimatedDepartureTime(routeOrder.getEstimatedDepartureTime());
        dto.setActualDepartureTime(routeOrder.getActualDepartureTime());
        dto.setStatus(routeOrder.getStatus());
        dto.setCreatedAt(routeOrder.getCreatedAt());

        return dto;
    }
}