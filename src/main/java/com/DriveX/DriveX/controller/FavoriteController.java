package com.DriveX.DriveX.controller;


import com.DriveX.DriveX.model.vehicle.Favorite;
import com.DriveX.DriveX.model.user.User;
import com.DriveX.DriveX.model.vehicle.Vehicle;
import com.DriveX.DriveX.repository.FavoriteRepository;
import com.DriveX.DriveX.repository.UserRepository;
import com.DriveX.DriveX.repository.VehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public FavoriteController(FavoriteRepository favoriteRepository,
                              UserRepository userRepository,
                              VehicleRepository vehicleRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/{userId}")
    public List<Vehicle> getFavorites(@PathVariable Long userId) {
        return favoriteRepository.findByUserId(userId)
                .stream()
                .map(Favorite::getVehicle)
                .collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/{userId}/{vehicleId}")
    public void addFavorite(@PathVariable Long userId,
                            @PathVariable Long vehicleId) {

        if (favoriteRepository.findByUserIdAndVehicleId(userId, vehicleId).isPresent()) {
            return;
        }

        User user = userRepository.findById(userId).orElseThrow();
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();

        favoriteRepository.save(new Favorite(user, vehicle));
    }

    @DeleteMapping("/{userId}/{vehicleId}")
    public void removeFavorite(@PathVariable Long userId,
                               @PathVariable Long vehicleId) {

        favoriteRepository.deleteByUserIdAndVehicleId(userId, vehicleId);
    }
}
