package com.DriveX.DriveX.controller;

import com.DriveX.DriveX.model.user.User;
import com.DriveX.DriveX.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable Long id) {
        return service.searchForId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User u) {
        u.setId(null);
        return ResponseEntity.ok(service.save(u));
    }

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
