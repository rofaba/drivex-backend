package com.DriveX.DriveX.repository;

import com.DriveX.DriveX.model.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByBrandContainingIgnoreCase(String brand);

    List<Vehicle> findByBrandAndModel(String brand, String model);

    List<Vehicle> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String brand, String model);

    List<Vehicle> findAllByOrderByBrandAsc();
    List<Vehicle> findByYear(int year);
    List<Vehicle> findByvehicle_type(String vehicle_type);
}