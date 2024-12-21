package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Users;
import com.myapp.money_planner.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    private final UsersService usersService;
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users createdUser = usersService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable Long userId) {
        Optional<Users> user = usersService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
        Optional<Users> user = usersService.getUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
        Optional<Users> user = usersService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username-or-email")
    public ResponseEntity<Users> getUserByUsernameOrEmail(@RequestParam String username, @RequestParam String email) {
        Optional<Users> user = usersService.getUserByUsernameOrEmail(username, email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody Users userDetails) {
        try {
            log.debug("Received password update request. Current: {}, New: {}",
                    userDetails.getCurrentPassword(), userDetails.getNewPassword());
            Users updatedUser = usersService.updateUser(userId, userDetails);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            log.error("Failed to update user: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{userId}/profile-photo")
    public ResponseEntity<Users> updateProfilePhoto(
            @PathVariable Long userId,
            @RequestBody Map<String, String> payload) {
        try {
            String photoUrl = payload.get("profilePhotoUrl");
            String photoName = payload.get("profilePhotoName");
            Users updatedUser = usersService.updateProfilePhoto(userId, photoUrl, photoName);
            return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean isDeleted = usersService.deleteUser(userId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Users user) {
        if (usersService.getUserByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already in use.");
        }
        if (usersService.getUserByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use.");
        }
        usersService.createUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Users> authenticatedUser = usersService.authenticateUser(loginRequest.getUsername(),
                loginRequest.getPassword());

        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }

        Users user = authenticatedUser.get();
        return ResponseEntity.ok(Map.of(
                "message", "Login successful.",
                "userId", user.getUserId(),
                "username", user.getUsername()));
    }

}
