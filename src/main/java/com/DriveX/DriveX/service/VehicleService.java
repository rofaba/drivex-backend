package com.DriveX.DriveX.service;

import com.DriveX.DriveX.model.vehicle.Vehicle;
import com.DriveX.DriveX.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository repo;

    public VehicleService(VehicleRepository repo) {
        this.repo = repo;
    }

    public List<Vehicle> findAll() {
        return repo.findAll();
    }

    public List<Vehicle> findAllByOrderByYearAsc() {return repo.findAllByOrderByBrandAsc();}

    public Optional<Vehicle> searchForId(Long id) {
        return repo.findById(id);
    }

    public Vehicle save(Vehicle v) {
        return repo.save(v);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<Vehicle> findByBrand(String brand) {
        return repo.findByBrandContainingIgnoreCase(brand);
    }

    public List<Vehicle> findByBrandAndModel(String brand, String model) {
        return repo.findByBrandAndModel(brand, model);
    }

    public List<Vehicle> searchByBrandOrModel(String term) {
        return repo.findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(term, term);
    }

    public List<Vehicle> findByYear(int year) {
        return repo.findByYear(year);
    }

    public List<Vehicle> findByVehicleType(String q) {
        List<String> types = Arrays.stream(q.split(","))
                .map(String::trim)
                .toList();

        return repo.findByVehicleTypeInIgnoreCase(types);
    }
    public List<Vehicle> findByYearBetween(Integer startYear, Integer endYear) {
        return repo.findByYearBetween(startYear, endYear);
    }

    public List<Vehicle> filter(String brand, String model, Integer year, String type) {
        if (brand != null && brand.isBlank()) brand = null;
        if (model != null && model.isBlank()) model = null;
        if (type  != null && type.isBlank())  type  = null;

        return repo.filter(brand, model, year, type);
    }

    public List<Vehicle> findFavoritesByUserId(Long userId) {
        return repo.findFavoritesByUserId(userId);
    }

}