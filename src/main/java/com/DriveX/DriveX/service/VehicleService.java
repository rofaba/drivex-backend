package com.DriveX.DriveX.service;

import com.DriveX.DriveX.model.vehicle.Vehicle;
import com.DriveX.DriveX.repository.VehicleRepository;
import org.springframework.stereotype.Service;

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

    public List<Vehicle> findByVehicle_type(String q) {return repo.findByvehicle_type(q);}
}