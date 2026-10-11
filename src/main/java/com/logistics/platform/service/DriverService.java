package com.logistics.platform.service;

import com.logistics.platform.dto.CreateDriverDTO;
import com.logistics.platform.dto.DriverResponseDTO;
import com.logistics.platform.dto.UpdateDriverDTO;
import com.logistics.platform.entity.Driver;
import com.logistics.platform.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // Get all drivers
    public List<DriverResponseDTO> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get driver by ID
    public Optional<DriverResponseDTO> getDriverById(Integer id) {
        return driverRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // Get drivers by status
    public List<DriverResponseDTO> getDriversByStatus(String status) {
        return driverRepository.findByStatus(status)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get drivers by vehicle type
    public List<DriverResponseDTO> getDriversByVehicleType(String vehicleType) {
        return driverRepository.findByVehicleType(vehicleType)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get drivers by user ID
    public List<DriverResponseDTO> getDriversByUserId(Integer userId) {
        return driverRepository.findByUserId(userId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Create driver
    public DriverResponseDTO createDriver(CreateDriverDTO request) {

        Driver driver = new Driver();

        driver.setUserId(request.getUserId());
        driver.setName(request.getName());
        driver.setPhone(request.getPhone());
        driver.setVehicleType(request.getVehicleType());
        driver.setVehicleCapacityKg(request.getVehicleCapacityKg());
        driver.setVehicleCapacityUnits(request.getVehicleCapacityUnits());
        driver.setCurrentLatitude(request.getCurrentLatitude());
        driver.setCurrentLongitude(request.getCurrentLongitude());
        driver.setStatus(request.getStatus());
        driver.setAvailabilityStartTime(request.getAvailabilityStartTime());
        driver.setAvailabilityEndTime(request.getAvailabilityEndTime());

        Driver savedDriver = driverRepository.save(driver);

        return toResponseDTO(savedDriver);
    }

    // Update driver
    public DriverResponseDTO updateDriver(Integer id, UpdateDriverDTO request) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (request.getName() != null) {
            driver.setName(request.getName());
        }

        if (request.getPhone() != null) {
            driver.setPhone(request.getPhone());
        }

        if (request.getVehicleType() != null) {
            driver.setVehicleType(request.getVehicleType());
        }

        if (request.getVehicleCapacityKg() != null) {
            driver.setVehicleCapacityKg(request.getVehicleCapacityKg());
        }

        if (request.getVehicleCapacityUnits() != null) {
            driver.setVehicleCapacityUnits(request.getVehicleCapacityUnits());
        }

        if (request.getCurrentLatitude() != null) {
            driver.setCurrentLatitude(request.getCurrentLatitude());
        }

        if (request.getCurrentLongitude() != null) {
            driver.setCurrentLongitude(request.getCurrentLongitude());
        }

        if (request.getStatus() != null) {
            driver.setStatus(request.getStatus());
        }

        if (request.getAvailabilityStartTime() != null) {
            driver.setAvailabilityStartTime(request.getAvailabilityStartTime());
        }

        if (request.getAvailabilityEndTime() != null) {
            driver.setAvailabilityEndTime(request.getAvailabilityEndTime());
        }

        Driver updatedDriver = driverRepository.save(driver);

        return toResponseDTO(updatedDriver);
    }

    // Delete driver
    public void deleteDriver(Integer id) {
        driverRepository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private DriverResponseDTO toResponseDTO(Driver driver) {

        DriverResponseDTO dto = new DriverResponseDTO();

        dto.setId(driver.getId());
        dto.setUserId(driver.getUserId());
        dto.setName(driver.getName());
        dto.setPhone(driver.getPhone());
        dto.setVehicleType(driver.getVehicleType());
        dto.setVehicleCapacityKg(driver.getVehicleCapacityKg());
        dto.setVehicleCapacityUnits(driver.getVehicleCapacityUnits());
        dto.setCurrentLatitude(driver.getCurrentLatitude());
        dto.setCurrentLongitude(driver.getCurrentLongitude());
        dto.setStatus(driver.getStatus());
        dto.setAvailabilityStartTime(driver.getAvailabilityStartTime());
        dto.setAvailabilityEndTime(driver.getAvailabilityEndTime());
        dto.setTotalDeliveries(driver.getTotalDeliveries());
        dto.setRating(driver.getRating());

        return dto;
    }
}