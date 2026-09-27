package com.logistics.platform.repository;

import com.logistics.platform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatus(String status);

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findByAssignedDriverId(Integer driverId);

    List<Order> findByAssignedRouteId(Integer routeId);

    List<Order> findByPriority(String priority);
}