package com.DriveX.DriveX.repository;

import com.DriveX.DriveX.model.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByBrandContainingIgnoreCase(String brand);

    List<Vehicle> findByBrandAndModel(String brand, String model);

    List<Vehicle> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String brand, String model);

    List<Vehicle> findAllByOrderByBrandAsc();

    List<Vehicle> findByYear(int year);

    List<Vehicle> findByVehicleTypeInIgnoreCase(List<String> types);

    List<Vehicle> findByYearBetween(Integer starYear, Integer endYear);




    @Query("""
       SELECT v
       FROM Vehicle v
       WHERE (:brand IS NULL OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
         AND (:model IS NULL OR LOWER(v.model) LIKE LOWER(CONCAT('%', :model, '%')))
         AND (:year IS NULL OR v.year = :year)
         AND (:type IS NULL OR LOWER(v.vehicleType) = LOWER(:type))
       """)
    List<Vehicle> filter(
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("year") Integer year,
            @Param("type") String type
    );



}