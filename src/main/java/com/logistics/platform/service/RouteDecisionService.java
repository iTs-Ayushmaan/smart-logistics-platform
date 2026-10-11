package com.logistics.platform.service;

import com.logistics.platform.dto.CreateRouteDecisionDTO;
import com.logistics.platform.dto.RouteDecisionResponseDTO;
import com.logistics.platform.dto.UpdateRouteDecisionDTO;
import com.logistics.platform.entity.RouteDecision;
import com.logistics.platform.repository.RouteDecisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteDecisionService {

    private final RouteDecisionRepository routeDecisionRepository;

    public RouteDecisionService(RouteDecisionRepository routeDecisionRepository) {
        this.routeDecisionRepository = routeDecisionRepository;
    }

    // Get all route decisions
    public List<RouteDecisionResponseDTO> getAllRouteDecisions() {
        return routeDecisionRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get route decision by ID
    public Optional<RouteDecisionResponseDTO> getRouteDecisionById(Integer id) {
        return routeDecisionRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // Get decisions by route ID
    public List<RouteDecisionResponseDTO> getRouteDecisionsByRouteId(
            Integer routeId) {

        return routeDecisionRepository.findByRouteId(routeId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get decisions by status
    public List<RouteDecisionResponseDTO> getRouteDecisionsByStatus(
            String decisionStatus) {

        return routeDecisionRepository.findByDecisionStatus(decisionStatus)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get decisions by approver
    public List<RouteDecisionResponseDTO> getRouteDecisionsByApprovedBy(
            Integer approvedBy) {

        return routeDecisionRepository.findByApprovedBy(approvedBy)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Create route decision
    public RouteDecisionResponseDTO createRouteDecision(
            CreateRouteDecisionDTO request) {

        RouteDecision decision = new RouteDecision();

        decision.setRouteId(request.getRouteId());
        decision.setDecisionStatus(request.getDecisionStatus());
        decision.setRiskScore(request.getRiskScore());
        decision.setTotalConstraintViolations(
                request.getTotalConstraintViolations());
        decision.setTimeWindowViolations(request.getTimeWindowViolations());
        decision.setCapacityViolations(request.getCapacityViolations());
        decision.setAvailabilityViolations(
                request.getAvailabilityViolations());
        decision.setDecisionReason(request.getDecisionReason());
        decision.setSuggestedAction(request.getSuggestedAction());
        decision.setApprovedBy(request.getApprovedBy());

        RouteDecision savedDecision =
                routeDecisionRepository.save(decision);

        return toResponseDTO(savedDecision);
    }

    // Update route decision
    public RouteDecisionResponseDTO updateRouteDecision(
            Integer id, UpdateRouteDecisionDTO request) {

        RouteDecision decision = routeDecisionRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Route decision not found"));

        if (request.getDecisionStatus() != null) {
            decision.setDecisionStatus(request.getDecisionStatus());
        }

        if (request.getRiskScore() != null) {
            decision.setRiskScore(request.getRiskScore());
        }

        if (request.getTotalConstraintViolations() != null) {
            decision.setTotalConstraintViolations(
                    request.getTotalConstraintViolations());
        }

        if (request.getTimeWindowViolations() != null) {
            decision.setTimeWindowViolations(
                    request.getTimeWindowViolations());
        }

        if (request.getCapacityViolations() != null) {
            decision.setCapacityViolations(
                    request.getCapacityViolations());
        }

        if (request.getAvailabilityViolations() != null) {
            decision.setAvailabilityViolations(
                    request.getAvailabilityViolations());
        }

        if (request.getDecisionReason() != null) {
            decision.setDecisionReason(request.getDecisionReason());
        }

        if (request.getSuggestedAction() != null) {
            decision.setSuggestedAction(request.getSuggestedAction());
        }

        if (request.getApprovedBy() != null) {
            decision.setApprovedBy(request.getApprovedBy());
        }

        RouteDecision updatedDecision =
                routeDecisionRepository.save(decision);

        return toResponseDTO(updatedDecision);
    }

    // Delete route decision
    public void deleteRouteDecision(Integer id) {
        routeDecisionRepository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private RouteDecisionResponseDTO toResponseDTO(
            RouteDecision decision) {

        RouteDecisionResponseDTO dto = new RouteDecisionResponseDTO();

        dto.setId(decision.getId());
        dto.setRouteId(decision.getRouteId());
        dto.setDecisionStatus(decision.getDecisionStatus());
        dto.setRiskScore(decision.getRiskScore());
        dto.setTotalConstraintViolations(
                decision.getTotalConstraintViolations());
        dto.setTimeWindowViolations(decision.getTimeWindowViolations());
        dto.setCapacityViolations(decision.getCapacityViolations());
        dto.setAvailabilityViolations(
                decision.getAvailabilityViolations());
        dto.setDecisionReason(decision.getDecisionReason());
        dto.setSuggestedAction(decision.getSuggestedAction());
        dto.setApprovedBy(decision.getApprovedBy());
        dto.setApprovedAt(decision.getApprovedAt());
        dto.setCreatedAt(decision.getCreatedAt());

        return dto;
    }
}