package com.logistics.platform.repository;

import com.logistics.platform.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> findByDriverId(Integer driverId);

    List<Route> findByRouteDate(LocalDate routeDate);

    List<Route> findByStatus(String status);
}