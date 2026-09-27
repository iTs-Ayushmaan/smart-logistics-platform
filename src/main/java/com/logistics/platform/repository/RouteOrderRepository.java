package com.logistics.platform.repository;

import com.logistics.platform.entity.RouteOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteOrderRepository extends JpaRepository<RouteOrder, Integer> {

    List<RouteOrder> findByRouteId(Integer routeId);

    List<RouteOrder> findByOrderId(Integer orderId);

    List<RouteOrder> findByStatus(String status);
}