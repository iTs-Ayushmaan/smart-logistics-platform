package com.logistics.platform.service;

import com.logistics.platform.dto.CreateGpsLogDTO;
import com.logistics.platform.dto.GpsLogResponseDTO;
import com.logistics.platform.dto.UpdateGpsLogDTO;
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

    // Get all GPS logs
    public List<GpsLogResponseDTO> getAllGpsLogs() {
        return gpsLogRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get GPS log by ID
    public Optional<GpsLogResponseDTO> getGpsLogById(Long id) {
        return gpsLogRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // Get GPS logs by driver ID
    public List<GpsLogResponseDTO> getGpsLogsByDriverId(Integer driverId) {
        return gpsLogRepository.findByDriverId(driverId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get GPS logs by route ID
    public List<GpsLogResponseDTO> getGpsLogsByRouteId(Integer routeId) {
        return gpsLogRepository.findByRouteId(routeId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get GPS logs by order ID
    public List<GpsLogResponseDTO> getGpsLogsByOrderId(Integer orderId) {
        return gpsLogRepository.findByCurrentOrderId(orderId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Create GPS log
    public GpsLogResponseDTO createGpsLog(CreateGpsLogDTO request) {

        GpsLog gpsLog = new GpsLog();

        gpsLog.setDriverId(request.getDriverId());
        gpsLog.setRouteId(request.getRouteId());
        gpsLog.setCurrentOrderId(request.getCurrentOrderId());
        gpsLog.setLatitude(request.getLatitude());
        gpsLog.setLongitude(request.getLongitude());
        gpsLog.setSpeedKmh(request.getSpeedKmh());
        gpsLog.setAccuracyMeters(request.getAccuracyMeters());

        GpsLog savedGpsLog = gpsLogRepository.save(gpsLog);

        return toResponseDTO(savedGpsLog);
    }

    // Update GPS log
    public GpsLogResponseDTO updateGpsLog(
            Long id,
            UpdateGpsLogDTO request) {

        GpsLog gpsLog = gpsLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GPS log not found"));

        if (request.getLatitude() != null) {
            gpsLog.setLatitude(request.getLatitude());
        }

        if (request.getLongitude() != null) {
            gpsLog.setLongitude(request.getLongitude());
        }

        if (request.getSpeedKmh() != null) {
            gpsLog.setSpeedKmh(request.getSpeedKmh());
        }

        if (request.getAccuracyMeters() != null) {
            gpsLog.setAccuracyMeters(request.getAccuracyMeters());
        }

        GpsLog updatedGpsLog = gpsLogRepository.save(gpsLog);

        return toResponseDTO(updatedGpsLog);
    }

    // Delete GPS log
    public void deleteGpsLog(Long id) {
        gpsLogRepository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private GpsLogResponseDTO toResponseDTO(GpsLog gpsLog) {

        GpsLogResponseDTO dto = new GpsLogResponseDTO();

        dto.setId(gpsLog.getId());
        dto.setDriverId(gpsLog.getDriverId());
        dto.setRouteId(gpsLog.getRouteId());
        dto.setCurrentOrderId(gpsLog.getCurrentOrderId());
        dto.setLatitude(gpsLog.getLatitude());
        dto.setLongitude(gpsLog.getLongitude());
        dto.setSpeedKmh(gpsLog.getSpeedKmh());
        dto.setAccuracyMeters(gpsLog.getAccuracyMeters());
        dto.setTimestamp(gpsLog.getTimestamp());

        return dto;
    }
}