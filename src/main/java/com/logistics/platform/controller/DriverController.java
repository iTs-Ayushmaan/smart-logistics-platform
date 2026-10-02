package com.logistics.platform.controller;

import com.logistics.platform.entity.Driver;
import com.logistics.platform.service.DriverService;
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

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Integer id) {
        return driverService.getDriverById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Driver> getDriversByStatus(@PathVariable String status) {
        return driverService.getDriversByStatus(status);
    }

    @GetMapping("/vehicle-type/{vehicleType}")
    public List<Driver> getDriversByVehicleType(
            @PathVariable String vehicleType) {
        return driverService.getDriversByVehicleType(vehicleType);
    }

    @GetMapping("/user/{userId}")
    public List<Driver> getDriversByUserId(
            @PathVariable Integer userId) {
        return driverService.getDriversByUserId(userId);
    }

    @PostMapping
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.createDriver(driver);
    }

    @PutMapping
    public Driver updateDriver(@RequestBody Driver driver) {
        return driverService.updateDriver(driver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Integer id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}