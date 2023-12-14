package com.delivery.restaurant.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userJpaRepository;

    @Autowired
    public UserService(UserRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }

    public User createUser(User user) {
        return userJpaRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userJpaRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setPhone(userDetails.getPhone());
        return userJpaRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        User user = userJpaRepository.findById(id).orElse(null);
        if (user == null) {
            return false;
        }
        userJpaRepository.delete(user);
        return true;
    }
}
