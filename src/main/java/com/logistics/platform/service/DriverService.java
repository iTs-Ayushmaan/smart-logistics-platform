package com.logistics.platform.service;

import com.logistics.platform.dto.CreateDriverDTO;
import com.logistics.platform.dto.DriverResponseDTO;
import com.logistics.platform.dto.UpdateDriverDTO;
import com.logistics.platform.entity.Driver;
import com.logistics.platform.entity.User;
import com.logistics.platform.repository.DriverRepository;
import com.logistics.platform.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    public DriverService(
            DriverRepository driverRepository,
            UserRepository userRepository
    ) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
    }

    public List<DriverResponseDTO> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<DriverResponseDTO> getDriverById(Integer id) {
        return driverRepository.findById(id)
                .map(this::toResponseDTO);
    }

    public List<DriverResponseDTO> getDriversByStatus(String status) {
        return driverRepository.findByStatus(
                        status.trim().toUpperCase(Locale.ROOT))
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<DriverResponseDTO> getDriversByVehicleType(
            String vehicleType) {

        return driverRepository.findByVehicleType(vehicleType)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<DriverResponseDTO> getDriversByUserId(Integer userId) {
        return driverRepository.findByUserId(userId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public DriverResponseDTO createDriver(CreateDriverDTO request) {

        // 1. Verify that the user exists.
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        // 2. Only DRIVER accounts can have driver profiles.
        if (!"DRIVER".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The selected user does not have the DRIVER role"
            );
        }

        // 3. Prevent duplicate driver profiles.
        if (driverRepository.existsByUserId(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A driver profile already exists for this user"
            );
        }

        Driver driver = new Driver();

        driver.setUserId(user.getId());
        driver.setName(request.getName().trim());
        driver.setPhone(request.getPhone().trim());
        driver.setVehicleType(request.getVehicleType());
        driver.setVehicleCapacityKg(request.getVehicleCapacityKg());
        driver.setVehicleCapacityUnits(request.getVehicleCapacityUnits());
        driver.setCurrentLatitude(request.getCurrentLatitude());
        driver.setCurrentLongitude(request.getCurrentLongitude());

        String status = request.getStatus();

        driver.setStatus(
                status == null || status.isBlank()
                        ? "INACTIVE"
                        : status.trim().toUpperCase(Locale.ROOT)
        );

        driver.setAvailabilityStartTime(
                request.getAvailabilityStartTime()
        );

        driver.setAvailabilityEndTime(
                request.getAvailabilityEndTime()
        );

        Driver savedDriver = driverRepository.save(driver);

        return toResponseDTO(savedDriver);
    }

    public DriverResponseDTO updateDriver(
            Integer id,
            UpdateDriverDTO request
    ) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Driver not found"
                ));

        if (request.getName() != null) {
            driver.setName(request.getName().trim());
        }

        if (request.getPhone() != null) {
            driver.setPhone(request.getPhone().trim());
        }

        if (request.getVehicleType() != null) {
            driver.setVehicleType(request.getVehicleType());
        }

        if (request.getVehicleCapacityKg() != null) {
            driver.setVehicleCapacityKg(request.getVehicleCapacityKg());
        }

        if (request.getVehicleCapacityUnits() != null) {
            driver.setVehicleCapacityUnits(
                    request.getVehicleCapacityUnits()
            );
        }

        if (request.getCurrentLatitude() != null) {
            driver.setCurrentLatitude(request.getCurrentLatitude());
        }

        if (request.getCurrentLongitude() != null) {
            driver.setCurrentLongitude(request.getCurrentLongitude());
        }

        if (request.getStatus() != null) {
            driver.setStatus(
                    request.getStatus().trim().toUpperCase(Locale.ROOT)
            );
        }

        if (request.getAvailabilityStartTime() != null) {
            driver.setAvailabilityStartTime(
                    request.getAvailabilityStartTime()
            );
        }

        if (request.getAvailabilityEndTime() != null) {
            driver.setAvailabilityEndTime(
                    request.getAvailabilityEndTime()
            );
        }

        Driver updatedDriver = driverRepository.save(driver);

        return toResponseDTO(updatedDriver);
    }

    public void deleteDriver(Integer id) {

        if (!driverRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Driver not found"
            );
        }

        driverRepository.deleteById(id);
    }

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