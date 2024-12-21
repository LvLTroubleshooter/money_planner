package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Users;
import com.myapp.money_planner.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private static final Logger log = LoggerFactory.getLogger(UsersService.class);

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users createUser(Users user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        return usersRepository.save(user);
    }

    public Optional<Users> getUserById(Long userId) {
        return usersRepository.findById(userId);
    }

    public Optional<Users> getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Optional<Users> getUserByUsernameOrEmail(String username, String email) {
        return usersRepository.findByUsernameOrEmail(username, email);
    }

    public Users updateUser(Long userId, Users userDetails) {
        log.debug("Updating user {} with details: {}", userId, userDetails);
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            // Handle password update first
            if (userDetails.getCurrentPassword() != null && !userDetails.getCurrentPassword().isEmpty()) {
                log.debug("Current password from request: {}", userDetails.getCurrentPassword());
                log.debug("Stored password: {}", user.getUserPassword());

                // Verify current password
                if (!userDetails.getCurrentPassword().equals(user.getUserPassword())) {
                    log.error("Current password is incorrect for user {}", userId);
                    throw new RuntimeException("Current password is incorrect");
                }

                // Validate new password
                if (userDetails.getNewPassword() == null || userDetails.getNewPassword().isEmpty()) {
                    log.error("New password is required for user {}", userId);
                    throw new RuntimeException("New password is required");
                }

                // Update password
                log.debug("Updating password from {} to {}", user.getUserPassword(), userDetails.getNewPassword());
                user.setUserPassword(userDetails.getNewPassword());
                log.debug("Password updated successfully");
            }

            // Handle other updates...
            if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
                // Check if username is already taken by another user
                Optional<Users> existingUser = usersRepository.findByUsername(userDetails.getUsername());
                if (existingUser.isPresent() && !existingUser.get().getUserId().equals(userId)) {
                    throw new RuntimeException("Username already taken");
                }
                user.setUsername(userDetails.getUsername());
            }

            if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
                // Check if email is already taken by another user
                Optional<Users> existingUser = usersRepository.findByEmail(userDetails.getEmail());
                if (existingUser.isPresent() && !existingUser.get().getUserId().equals(userId)) {
                    throw new RuntimeException("Email already taken");
                }
                user.setEmail(userDetails.getEmail());
            }

            // Handle profile photo
            if (userDetails.getProfilePhotoUrl() == null) {
                user.setProfilePhotoUrl(null);
                user.setProfilePhotoName(null);
            } else if (!userDetails.getProfilePhotoUrl().equals(user.getProfilePhotoUrl())) {
                user.setProfilePhotoUrl(userDetails.getProfilePhotoUrl());
                user.setProfilePhotoName(userDetails.getProfilePhotoName());
            }

            log.debug("Saving updated user {}", userId);
            return usersRepository.save(user);
        }
        log.error("User not found with ID: {}", userId);
        return null;
    }

    public boolean deleteUser(Long userId) {
        if (usersRepository.existsById(userId)) {
            usersRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> authenticateUser(String username, String password) {
        // Retrieve the user by username
        Optional<Users> userOptional = usersRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            // Directly compare the passwords (plaintext comparison since no hashing)
            if (password.equals(user.getUserPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Users updateProfilePhoto(Long userId, String photoUrl, String photoName) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setProfilePhotoUrl(photoUrl);
            user.setProfilePhotoName(photoName);
            return usersRepository.save(user);
        }
        return null;
    }

}
