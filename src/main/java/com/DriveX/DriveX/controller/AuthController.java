package com.DriveX.DriveX.controller;

import com.DriveX.DriveX.model.user.LoginRequest;
import com.DriveX.DriveX.model.user.User;
import com.DriveX.DriveX.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {

        return service.login(request.getEmail(), request.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (service.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email ya registrado");
        }

        User saved = service.save(user);
        return ResponseEntity.ok(saved);
    }
}