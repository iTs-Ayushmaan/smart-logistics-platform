package com.logistics.platform.controller;

import com.logistics.platform.dto.CreateDriverDTO;
import com.logistics.platform.dto.DriverResponseDTO;
import com.logistics.platform.dto.UpdateDriverDTO;
import com.logistics.platform.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // Get all drivers
    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    // Get driver by ID
    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(
            @PathVariable Integer id) {

        return driverService.getDriverById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get drivers by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<DriverResponseDTO>> getDriversByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                driverService.getDriversByStatus(status)
        );
    }

    // Get drivers by vehicle type
    @GetMapping("/vehicle-type/{vehicleType}")
    public ResponseEntity<List<DriverResponseDTO>> getDriversByVehicleType(
            @PathVariable String vehicleType) {

        return ResponseEntity.ok(
                driverService.getDriversByVehicleType(vehicleType)
        );
    }

    // Get drivers by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DriverResponseDTO>> getDriversByUserId(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(
                driverService.getDriversByUserId(userId)
        );
    }

    // Create driver
    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(
            @Valid @RequestBody CreateDriverDTO request) {

        return ResponseEntity.ok(
                driverService.createDriver(request)
        );
    }

    // Update driver
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateDriverDTO request) {

        return ResponseEntity.ok(
                driverService.updateDriver(id, request)
        );
    }

    // Delete driver
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(
            @PathVariable Integer id) {

        driverService.deleteDriver(id);

        return ResponseEntity.noContent().build();
    }
}