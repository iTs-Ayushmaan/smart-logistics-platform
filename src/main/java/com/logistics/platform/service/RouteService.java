package com.logistics.platform.service;

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

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteById(Integer id) {
        return routeRepository.findById(id);
    }

    public List<Route> getRoutesByDriverId(Integer driverId) {
        return routeRepository.findByDriverId(driverId);
    }

    public List<Route> getRoutesByDate(LocalDate routeDate) {
        return routeRepository.findByRouteDate(routeDate);
    }

    public List<Route> getRoutesByStatus(String status) {
        return routeRepository.findByStatus(status);
    }

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    public Route updateRoute(Route route) {
        return routeRepository.save(route);
    }

    public void deleteRoute(Integer id) {
        routeRepository.deleteById(id);
    }
}