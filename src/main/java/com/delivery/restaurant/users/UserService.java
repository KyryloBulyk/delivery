package com.delivery.restaurant.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userJpaRepository) {
        this.userRepository = userJpaRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return false;
        }
        userRepository.delete(user);
        return true;
    }

    public User authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        } else {
            return null;
        }
    }
}
