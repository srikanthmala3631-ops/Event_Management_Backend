package com.eventapp.controller;

import com.eventapp.dto.LoginRequest;
import com.eventapp.dto.RegisterUserRequest;
import com.eventapp.model.User;
import com.eventapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Handles account creation and login.
 *
 * NOTE ON SIMPLICITY: This project skips Spring Security / JWT tokens to
 * keep things beginner-friendly. Login just checks the email + password
 * against the database and returns the user's info. The React app then
 * remembers "who is logged in" in the browser (localStorage).
 * This is NOT secure enough for a real production app, but it's enough
 * to learn how the pieces (React <-> Spring Boot <-> MySQL) fit together.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // POST /api/auth/register - create a new account (role defaults to USER)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "An account with this email already exists."));
        }

        User user = new User(request.getName(), request.getEmail(), request.getPassword(), "USER");
        User saved = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(toSafeUserMap(saved));
    }

    // POST /api/auth/login - check credentials and return the user's info
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password."));
        }

        return ResponseEntity.ok(toSafeUserMap(userOpt.get()));
    }

    // Helper: never send the password back to the frontend
    private Map<String, Object> toSafeUserMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("role", user.getRole());
        return map;
    }
}
