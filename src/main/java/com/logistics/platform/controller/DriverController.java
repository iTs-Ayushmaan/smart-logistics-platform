package com.logistics.platform.controller;

import com.logistics.platform.dto.CreateDriverDTO;
import com.logistics.platform.dto.DriverResponseDTO;
import com.logistics.platform.dto.UpdateDriverDTO;
import com.logistics.platform.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@PreAuthorize("hasRole('ADMIN')")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(
            @PathVariable Integer id) {

        return driverService.getDriverById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DriverResponseDTO>> getDriversByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                driverService.getDriversByStatus(status)
        );
    }

    @GetMapping("/vehicle-type/{vehicleType}")
    public ResponseEntity<List<DriverResponseDTO>> getDriversByVehicleType(
            @PathVariable String vehicleType) {

        return ResponseEntity.ok(
                driverService.getDriversByVehicleType(vehicleType)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DriverResponseDTO>> getDriversByUserId(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(
                driverService.getDriversByUserId(userId)
        );
    }

    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(
            @Valid @RequestBody CreateDriverDTO request) {

        return ResponseEntity.status(201)
                .body(driverService.createDriver(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateDriverDTO request) {

        return ResponseEntity.ok(
                driverService.updateDriver(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(
            @PathVariable Integer id) {

        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}