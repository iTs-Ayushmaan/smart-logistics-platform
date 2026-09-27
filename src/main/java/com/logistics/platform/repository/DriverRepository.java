package com.logistics.platform.repository;

import com.logistics.platform.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    List<Driver> findByStatus(String status);

    List<Driver> findByVehicleType(String vehicleType);

    List<Driver> findByUserId(Integer userId);
}