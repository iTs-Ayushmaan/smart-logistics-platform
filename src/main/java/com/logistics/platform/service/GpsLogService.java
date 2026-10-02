package com.logistics.platform.service;

import com.logistics.platform.entity.GpsLog;
import com.logistics.platform.repository.GpsLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GpsLogService {

    private final GpsLogRepository gpsLogRepository;

    public GpsLogService(GpsLogRepository gpsLogRepository) {
        this.gpsLogRepository = gpsLogRepository;
    }

    public List<GpsLog> getAllGpsLogs() {
        return gpsLogRepository.findAll();
    }

    public Optional<GpsLog> getGpsLogById(Long id) {
        return gpsLogRepository.findById(id);
    }

    public List<GpsLog> getGpsLogsByDriverId(Integer driverId) {
        return gpsLogRepository.findByDriverId(driverId);
    }

    public List<GpsLog> getGpsLogsByRouteId(Integer routeId) {
        return gpsLogRepository.findByRouteId(routeId);
    }

    public List<GpsLog> getGpsLogsByOrderId(Integer orderId) {
        return gpsLogRepository.findByCurrentOrderId(orderId);
    }

    public GpsLog createGpsLog(GpsLog gpsLog) {
        return gpsLogRepository.save(gpsLog);
    }

    public GpsLog updateGpsLog(GpsLog gpsLog) {
        return gpsLogRepository.save(gpsLog);
    }

    public void deleteGpsLog(Long id) {
        gpsLogRepository.deleteById(id);
    }
}