package com.DriveX.DriveX.controller;

import com.DriveX.DriveX.model.rental.Rental;
import com.DriveX.DriveX.service.RentalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Rental> create(@RequestBody Rental rental) {
        Rental created = service.createRental(rental);
        return ResponseEntity.created(URI.create("/api/rentals/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRental(id));
    }

    @GetMapping
    public ResponseEntity<List<Rental>> list(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long vehicleId) {
        if (userId != null) return ResponseEntity.ok(service.findByUser(userId));
        if (vehicleId != null) return ResponseEntity.ok(service.findByVehicle(vehicleId));
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        service.cancelRental(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable Long id) {
        service.completeRental(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> availability(
            @RequestParam Long vehicleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(service.isAvailable(vehicleId, start, end));
    }
}
