package com.DriveX.DriveX.service;

import com.DriveX.DriveX.model.user.User;
import com.DriveX.DriveX.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> searchForId(Long id) {
        return repo.findById(id);
    }

    public User save(User v) {
        // ⚠️ Aquí asumimos que v.getPassword() viene en texto plano
        String rawPassword = v.getPassword_hash();
        String hashed = passwordEncoder.encode(rawPassword);
        v.setPassword_hash(hashed);
        return repo.save(v);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = repo.findByEmail(email);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        // Aquí comparamos el texto plano con el hash usando BCrypt
        if (!passwordEncoder.matches(password, user.getPassword_hash())) {
            return Optional.empty();
        }

        return Optional.of(user); // login OK
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> userOpt = repo.findByEmail(email);
        return userOpt;
    }

    public List<User> searchByUsername(String q) {
        return repo.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q);
    }
}