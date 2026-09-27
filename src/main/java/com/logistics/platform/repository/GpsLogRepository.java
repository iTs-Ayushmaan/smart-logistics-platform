package com.logistics.platform.repository;

import com.logistics.platform.entity.GpsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpsLogRepository extends JpaRepository<GpsLog, Long> {

    List<GpsLog> findByDriverId(Integer driverId);

    List<GpsLog> findByRouteId(Integer routeId);

    List<GpsLog> findByCurrentOrderId(Integer orderId);
}