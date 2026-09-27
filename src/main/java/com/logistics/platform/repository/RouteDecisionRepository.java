package com.logistics.platform.repository;

import com.logistics.platform.entity.RouteDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteDecisionRepository extends JpaRepository<RouteDecision, Integer> {

    List<RouteDecision> findByRouteId(Integer routeId);

    List<RouteDecision> findByDecisionStatus(String decisionStatus);

    List<RouteDecision> findByApprovedBy(Integer approvedBy);
}