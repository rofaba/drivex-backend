package com.DriveX.DriveX.repository;


import com.DriveX.DriveX.model.vehicle.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUserIdAndVehicleId(Long userId, Long vehicleId);

    List<Favorite> findByUserId(Long userId);

    void deleteByUserIdAndVehicleId(Long userId, Long vehicleId);
}