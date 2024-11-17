package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Users;
import com.myapp.money_planner.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;


@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Create
    public Users createUser(Users user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Or let DB handle it
        }
        return usersRepository.save(user);
    }

    // Read
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

    // Update
    public Users updateUser(Long userId, Users userDetails) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setUserPassword(userDetails.getUserPassword());
            return usersRepository.save(user);
        }
        return null;
    }

    // Delete
    public boolean deleteUser(Long userId) {
        if (usersRepository.existsById(userId)) {
            usersRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    // List all users
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
