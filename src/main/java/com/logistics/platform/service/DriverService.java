package com.logistics.platform.service;

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

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(Integer id) {
        return driverRepository.findById(id);
    }

    public List<Driver> getDriversByStatus(String status) {
        return driverRepository.findByStatus(status);
    }

    public List<Driver> getDriversByVehicleType(String vehicleType) {
        return driverRepository.findByVehicleType(vehicleType);
    }

    public List<Driver> getDriversByUserId(Integer userId) {
        return driverRepository.findByUserId(userId);
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public void deleteDriver(Integer id) {
        driverRepository.deleteById(id);
    }
}