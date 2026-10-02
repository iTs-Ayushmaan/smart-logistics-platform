package com.logistics.platform.service;

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

    public List<RouteOrder> getAllRouteOrders() {
        return routeOrderRepository.findAll();
    }

    public Optional<RouteOrder> getRouteOrderById(Integer id) {
        return routeOrderRepository.findById(id);
    }

    public List<RouteOrder> getRouteOrdersByRouteId(Integer routeId) {
        return routeOrderRepository.findByRouteId(routeId);
    }

    public List<RouteOrder> getRouteOrdersByOrderId(Integer orderId) {
        return routeOrderRepository.findByOrderId(orderId);
    }

    public List<RouteOrder> getRouteOrdersByStatus(String status) {
        return routeOrderRepository.findByStatus(status);
    }

    public RouteOrder createRouteOrder(RouteOrder routeOrder) {
        return routeOrderRepository.save(routeOrder);
    }

    public RouteOrder updateRouteOrder(RouteOrder routeOrder) {
        return routeOrderRepository.save(routeOrder);
    }

    public void deleteRouteOrder(Integer id) {
        routeOrderRepository.deleteById(id);
    }
}