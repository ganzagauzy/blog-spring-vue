package com.blog.blog.services;

import com.blog.blog.model.User;
import com.blog.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) throws ChangeSetPersister.NotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public User createUser(User user) {
        // Hash the user's password before saving to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User userDetails) throws ChangeSetPersister.NotFoundException {
        User user = getUserById(userId);

        // Update other fields as needed
        user.setName(userDetails.getName());
        user.setRole(userDetails.getRole());
        user.setEmail(userDetails.getEmail());

        // Hash the new password before updating (if provided)
        if (userDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        // Assuming you have a method in UserRepository for fetching a user by email and password
        return userRepository.findByEmailAndPassword(email, password);
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

