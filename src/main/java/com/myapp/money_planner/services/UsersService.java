package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Users;
import com.myapp.money_planner.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

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
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            if (userDetails.getProfilePhotoUrl() != null) {
                user.setProfilePhotoUrl(userDetails.getProfilePhotoUrl());
            }
            if (userDetails.getUserPassword() != null && !userDetails.getUserPassword().isEmpty()) {
                user.setUserPassword(userDetails.getUserPassword());
            }
            return usersRepository.save(user);
        }
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

    public Users updateProfilePhoto(Long userId, String photoUrl) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setProfilePhotoUrl(photoUrl);
            return usersRepository.save(user);
        }
        return null;
    }

}
