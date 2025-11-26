package com.DriveX.DriveX.controller;

import com.DriveX.DriveX.model.user.User;
import com.DriveX.DriveX.model.vehicle.Vehicle;
import com.DriveX.DriveX.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")  // OJO: se suma al /api del context path
@CrossOrigin(origins = "*")    // Para que web, Android y JavaFX puedan llamar
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // GET http://localhost:8080/api/vehiculos
    @GetMapping
    public List<User> listar() {
        return service.findAll();
    }

    // GET http://localhost:8080/api/vehiculos/1
    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable Long id) {
        return service.searchForId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST http://localhost:8080/api/vehiculos
    @PostMapping
    public ResponseEntity<User> add(@RequestBody User u) {
        u.setId(null);
        return ResponseEntity.ok(service.save(u));
    }

    // PUT http://localhost:8080/api/vehiculos/1
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
        return service.searchForId(id)
                .map(existente -> {
                    u.setId(id);
                    return ResponseEntity.ok(service.save(u));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<User> search(@RequestParam String q) {
        return service.searchByUsername(q);
    }
}
