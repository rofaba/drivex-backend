package com.DriveX.DriveX.repository;


import com.DriveX.DriveX.model.vehicle.Favorite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser_Id(Long userId);

    Optional<Favorite> findByUser_IdAndVehicle_Id(Long userId, Long vehicleId);

    @Transactional
    void deleteByUser_IdAndVehicle_Id(Long userId, Long vehicleId);
}