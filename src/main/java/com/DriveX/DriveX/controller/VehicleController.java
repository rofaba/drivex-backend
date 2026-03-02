package com.DriveX.DriveX.controller;

import com.DriveX.DriveX.model.vehicle.Brand;
import com.DriveX.DriveX.model.vehicle.Vehicle;
import com.DriveX.DriveX.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vehicle> listar(@RequestParam(required = false) String marca) {
        if (marca != null && !marca.isBlank()) {
            return service.findByBrand(marca);
        }
        return service.findAllByOrderByYearAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> find(@PathVariable Long id) {
        return service.searchForId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vehicle> add(@RequestBody Vehicle v) {
        v.setId(null);

        if (v.getReference() == null || v.getReference().isBlank()) {
            v.setReference(generateReference(v));
        }

        return ResponseEntity.ok(service.save(v));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody Vehicle body) {
        return service.searchForId(id)
                .map(existing -> {

                    existing.setBrand(body.getBrand());
                    existing.setModel(body.getModel());
                    existing.setVehicleType(body.getVehicleType());
                    existing.setFuelType(body.getFuelType());

                    existing.setYear(body.getYear());
                    existing.setPrice(body.getPrice());
                    existing.setMileage(body.getMileage());
                    existing.setHp(body.getHp());
                    existing.setDoors(body.getDoors());

                    existing.setAutonomy(body.getAutonomy());
                    existing.setAverageconsumption(body.getAverageconsumption());

                    existing.setDescription(body.getDescription());
                    existing.setExtras(body.getExtras());
                    existing.setOffers(body.getOffers());

                    return ResponseEntity.ok(service.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/brand")
    public List<Vehicle> searchBrand(@RequestParam String brand) {
        return service.findByBrand(brand);
    }

    @GetMapping("/brandmodel")
    public List<Vehicle> searchBrandModel(@RequestParam String brand, @RequestParam String model) {
        return service.findByBrandAndModel(brand, model);
    }

    @GetMapping("/search")
    public List<Vehicle> search(@RequestParam String q) {
        return service.searchByBrandOrModel(q);
    }

    @GetMapping("/year")
    public List<Vehicle> searchYear(@RequestParam int year) {
        return service.findByYear(year);
    }

    @GetMapping("/vehicleType")
    public List<Vehicle> findByVehicleType(@RequestParam String q) {
        return service.findByVehicleType(q);
    }

    @GetMapping("/brands")
    public Brand[] getBrands() {
        return Arrays.stream(Brand.values())
                .sorted(Comparator.comparing(Enum::name))
                .toArray(Brand[]::new);
    }


    @GetMapping("/year-range")
    public List<Vehicle> findByYearRange(
            @RequestParam Integer startYear,
            @RequestParam Integer endYear) {
        return service.findByYearBetween(startYear, endYear);
    }

    @GetMapping("/filter")
    public List<Vehicle> filter(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String vehicleType
    ) {
        return service.filter(brand, model, year, vehicleType);
    }

    @GetMapping("/offers")
    public List<Vehicle> findByOffers() {
        return service.findByOffers();
    }


    @PatchMapping("/{id}/offers")
    public ResponseEntity<?> updateOffers(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String offers = body.get("offers");

        service.updateOffers(id, offers);

        return ResponseEntity.ok().build();
    }

    private String generateReference(Vehicle v) {
        String brand = v.getBrand() != null ? v.getBrand() : "GEN";
        String brandPart = brand
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();

        if (brandPart.length() >= 3) {
            brandPart = brandPart.substring(0, 3);
        } else {
            brandPart = String.format("%-3s", brandPart).replace(' ', 'X');
        }

        int year = (v.getYear() != null && v.getYear() > 0)
                ? v.getYear()
                : LocalDate.now().getYear();
        String yearPart = String.valueOf(year);

        String randomPart = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 4)
                .toUpperCase();

        return brandPart + yearPart + randomPart;
    }
}