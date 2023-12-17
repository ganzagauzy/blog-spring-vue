package com.blog.blog.controller;

import com.blog.blog.model.User;
import com.blog.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        try {
            return ResponseEntity.ok(userService.updateUser(userId, userDetails));
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        String email = loginRequest.getEmail();
        String plainTextPassword = loginRequest.getPassword();

        // Retrieve the user from the database by email
        User user = userService.getUserByEmail(email);

        if (user != null) {
            // Get the stored encrypted password from the user object
            String storedEncryptedPassword = user.getPassword();

            // Check if the provided password matches the stored encrypted password
            if (passwordEncoder.matches(plainTextPassword, storedEncryptedPassword)) {
                // Passwords match, generate and include a token in the response
                String token = generateRandomToken();

                // Return user and token in the response as a Map
                Map<String, Object> response = new HashMap<>();
                response.put("user", user);
                response.put("token", token);

                return ResponseEntity.ok(response);
            } else {
                // Invalid credentials
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    // Dummy token generation method for demonstration purposes
    private String generateRandomToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
