package com.logistics.platform.service;

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

    public List<RouteDecision> getAllRouteDecisions() {
        return routeDecisionRepository.findAll();
    }

    public Optional<RouteDecision> getRouteDecisionById(Integer id) {
        return routeDecisionRepository.findById(id);
    }

    public List<RouteDecision> getRouteDecisionsByRouteId(Integer routeId) {
        return routeDecisionRepository.findByRouteId(routeId);
    }

    public List<RouteDecision> getRouteDecisionsByStatus(String decisionStatus) {
        return routeDecisionRepository.findByDecisionStatus(decisionStatus);
    }

    public List<RouteDecision> getRouteDecisionsByApprovedBy(Integer approvedBy) {
        return routeDecisionRepository.findByApprovedBy(approvedBy);
    }

    public RouteDecision createRouteDecision(RouteDecision routeDecision) {
        return routeDecisionRepository.save(routeDecision);
    }

    public RouteDecision updateRouteDecision(RouteDecision routeDecision) {
        return routeDecisionRepository.save(routeDecision);
    }

    public void deleteRouteDecision(Integer id) {
        routeDecisionRepository.deleteById(id);
    }
}