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

@RestController
@RequestMapping("/vehicles")  // OJO: se suma al /api del context path
@CrossOrigin(origins = "*")    // Para que web, Android y JavaFX puedan llamar
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    // GET http://localhost:8080/api/vehicles
    @GetMapping
    public List<Vehicle> listar(@RequestParam(required = false) String marca) {
        if (marca != null && !marca.isBlank()) {
            return service.findByBrand(marca);
        }
        return service.findAllByOrderByYearAsc();
    }

    // GET http://localhost:8080/api/vehicles/1
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> find(@PathVariable Long id) {
        return service.searchForId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST http://localhost:8080/api/vehicles
    @PostMapping
    public ResponseEntity<Vehicle> add(@RequestBody Vehicle v) {
        v.setId(null);

        if (v.getReference() == null || v.getReference().isBlank()) {
            v.setReference(generateReference(v));
        }

        return ResponseEntity.ok(service.save(v));
    }

    // PUT http://localhost:8080/api/vehicles/1
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody Vehicle v) {
        return service.searchForId(id)
                .map(existente -> {
                    v.setId(id);
                    // Si quieres que no cambie la referencia al editar, copia la que ya tenía:
                    // v.setReference(existente.getReference());
                    return ResponseEntity.ok(service.save(v));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE http://localhost:8080/api/vehicles/1
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
    @GetMapping("/vehicle_type")
    public List<Vehicle> findByVehicle_type(@RequestParam String q) {
        return service.findByVehicle_type(q);
    }

    @GetMapping("/brands")
    public Brand[] getBrands() {
        return Arrays.stream(Brand.values())
                .sorted(Comparator.comparing(Enum::name))
                .toArray(Brand[]::new);
    }
    // =========================
    //   GENERADOR DE REFERENCIA
    // =========================
    private String generateReference(Vehicle v) {
        // Parte de la marca
        String brand = v.getBrand() != null ? v.getBrand() : "GEN";
        String brandPart = brand
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();

        if (brandPart.length() >= 3) {
            brandPart = brandPart.substring(0, 3);
        } else {
            // Rellena con X si es muy corta
            brandPart = String.format("%-3s", brandPart).replace(' ', 'X');
        }

        // Año (campo del vehículo o año actual si viene null/0)
        int year = (v.getYear() != null && v.getYear() > 0)
                ? v.getYear()
                : LocalDate.now().getYear();
        String yearPart = String.valueOf(year);

        // Parte aleatoria
        String randomPart = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 4)
                .toUpperCase();

        // Ejemplo: BMW2024A1B2
        return brandPart + yearPart + randomPart;
    }
}