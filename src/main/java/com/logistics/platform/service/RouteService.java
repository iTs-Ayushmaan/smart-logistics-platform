package com.logistics.platform.service;

import com.logistics.platform.dto.CreateRouteDTO;
import com.logistics.platform.dto.RouteResponseDTO;
import com.logistics.platform.dto.UpdateRouteDTO;
import com.logistics.platform.entity.Route;
import com.logistics.platform.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    // Get all routes
    public List<RouteResponseDTO> getAllRoutes() {
        return routeRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get route by ID
    public Optional<RouteResponseDTO> getRouteById(Integer id) {
        return routeRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // Get routes by driver ID
    public List<RouteResponseDTO> getRoutesByDriverId(Integer driverId) {
        return routeRepository.findByDriverId(driverId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get routes by date
    public List<RouteResponseDTO> getRoutesByDate(LocalDate routeDate) {
        return routeRepository.findByRouteDate(routeDate)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get routes by status
    public List<RouteResponseDTO> getRoutesByStatus(String status) {
        return routeRepository.findByStatus(status)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Create route
    public RouteResponseDTO createRoute(CreateRouteDTO request) {

        Route route = new Route();





        route.setDriverId(request.getDriverId());
        route.setRouteDate(request.getRouteDate());
        if (request.getStatus() != null) {
            route.setStatus(request.getStatus());
        }
        route.setRouteSequence(request.getRouteSequence());
        if (request.getOptimizedBy() != null) {
            route.setOptimizedBy(request.getOptimizedBy());
        }

        Route savedRoute = routeRepository.save(route);

        return toResponseDTO(savedRoute);
    }

    // Update route
    public RouteResponseDTO updateRoute(Integer id, UpdateRouteDTO request) {

        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        if (request.getRouteDate() != null) {
            route.setRouteDate(request.getRouteDate());
        }

        if (request.getStatus() != null) {
            route.setStatus(request.getStatus());
        }

        if (request.getRouteSequence() != null) {
            route.setRouteSequence(request.getRouteSequence());
        }

        if (request.getOptimizedBy() != null) {
            route.setOptimizedBy(request.getOptimizedBy());
        }

        Route updatedRoute = routeRepository.save(route);

        return toResponseDTO(updatedRoute);
    }

    // Delete route
    public void deleteRoute(Integer id) {
        routeRepository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private RouteResponseDTO toResponseDTO(Route route) {

        RouteResponseDTO dto = new RouteResponseDTO();

        dto.setId(route.getId());
        dto.setDriverId(route.getDriverId());
        dto.setRouteDate(route.getRouteDate());
        dto.setStatus(route.getStatus());
        dto.setEstimatedTotalDistanceKm(route.getEstimatedTotalDistanceKm());
        dto.setActualTotalDistanceKm(route.getActualTotalDistanceKm());

        dto.setEstimatedTotalTimeMinutes(route.getEstimatedTotalTimeMinutes());
        dto.setActualTotalTimeMinutes(route.getActualTotalTimeMinutes());

        dto.setEstimatedCostRupees(route.getEstimatedCostRupees());
        dto.setActualCostRupees(route.getActualCostRupees());

        dto.setOptimizationDurationSeconds(route.getOptimizationDurationSeconds());
//        dto.setTotalDistanceKm(route.getTotalDistanceKm());
//        dto.setEstimatedTimeMinutes(route.getEstimatedTimeMinutes());
//        dto.setTotalCost(route.getTotalCost());
        dto.setTotalOrders(route.getTotalOrders());
        dto.setCompletedOrders(route.getCompletedOrders());
        dto.setRouteSequence(route.getRouteSequence());
        dto.setOptimizedBy(route.getOptimizedBy());
        dto.setOptimizationTimestamp(route.getOptimizationTimestamp());
//        dto.setOptimizationDurationMs(route.getOptimizationDurationMs());
        dto.setCreatedAt(route.getCreatedAt());
        dto.setUpdatedAt(route.getUpdatedAt());

        return dto;
    }
}